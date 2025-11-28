package com.example.proyectofinal.service;

import com.example.proyectofinal.Exception.ResourceNotFoundException;
import com.example.proyectofinal.Model.ConsumoRepuesto;
import com.example.proyectofinal.Model.Repuesto;
import com.example.proyectofinal.Model.OrdenMantenimiento;
import com.example.proyectofinal.Repository.ConsumoRepuestoRepository;
import com.example.proyectofinal.Repository.RepuestoRepository;
import com.example.proyectofinal.Repository.OrdenMantenimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ConsumoRepuestoService {

    @Autowired
    private ConsumoRepuestoRepository consumoRepuestoRepository;

    @Autowired
    private RepuestoRepository repuestoRepository;

    @Autowired
    private OrdenMantenimientoRepository ordenMantenimientoRepository;

    // ... (los métodos helper y de stock se mantienen igual)
    private Repuesto getManagedRepuesto(Long repuestoId) {
        if (repuestoId == null) throw new IllegalArgumentException("El ID del repuesto no puede ser nulo.");
        return repuestoRepository.findById(repuestoId)
                .orElseThrow(() -> new ResourceNotFoundException("Repuesto no encontrado con id: " + repuestoId));
    }

    private OrdenMantenimiento getManagedOrden(Long ordenId) {
        if (ordenId == null) throw new IllegalArgumentException("El ID de la orden no puede ser nulo.");
        return ordenMantenimientoRepository.findById(ordenId)
                .orElseThrow(() -> new ResourceNotFoundException("Orden de Mantenimiento no encontrada con id: " + ordenId));
    }

    private void updateStock(Repuesto repuesto, int changeInQuantity) {
        int newQuantity = repuesto.getCantidad() + changeInQuantity;
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Stock insuficiente. No se puede consumir más de lo disponible.");
        }
        repuesto.setCantidad(newQuantity);
        repuestoRepository.save(repuesto);
    }


    @Transactional(readOnly = true)
    public List<ConsumoRepuesto> getAllConsumosRepuesto() {
        return consumoRepuestoRepository.findAllWithRelations();
    }

    @Transactional(readOnly = true)
    public Optional<ConsumoRepuesto> getConsumoRepuestoById(Long id) {
        // Usamos el nuevo método para asegurar que se carguen las relaciones también aquí
        return consumoRepuestoRepository.findByIdWithRelations(id);
    }

    @Transactional
    public ConsumoRepuesto saveConsumoRepuesto(ConsumoRepuesto consumoRepuesto) {
        Repuesto repuestoManaged = getManagedRepuesto(consumoRepuesto.getRepuesto().getRepuestoId());
        OrdenMantenimiento ordenManaged = getManagedOrden(consumoRepuesto.getOrdenMantenimiento().getOrdenId());

        updateStock(repuestoManaged, -consumoRepuesto.getCantidad());

        consumoRepuesto.setRepuesto(repuestoManaged);
        consumoRepuesto.setOrdenMantenimiento(ordenManaged);

        ConsumoRepuesto savedConsumo = consumoRepuestoRepository.save(consumoRepuesto);

        // 🚨 CAMBIO CLAVE: Volvemos a buscar la entidad con sus relaciones para devolverla completa
        return consumoRepuestoRepository.findByIdWithRelations(savedConsumo.getConsumoId())
                .orElseThrow(() -> new IllegalStateException("No se pudo encontrar el consumo recién guardado."));
    }

    @Transactional
    public ConsumoRepuesto updateConsumoRepuesto(Long id, ConsumoRepuesto consumoDetails) {
        ConsumoRepuesto consumo = consumoRepuestoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consumo de repuesto no encontrado con id: " + id));

        int oldQuantity = consumo.getCantidad();
        int newQuantity = consumoDetails.getCantidad();
        int quantityDifference = newQuantity - oldQuantity;

        Repuesto repuestoManaged = getManagedRepuesto(consumoDetails.getRepuesto().getRepuestoId());
        updateStock(repuestoManaged, -quantityDifference);

        consumo.setFechaEntrada(consumoDetails.getFechaEntrada());
        consumo.setFechaSalida(consumoDetails.getFechaSalida());
        consumo.setCantidad(newQuantity);
        consumo.setRepuesto(repuestoManaged);
        consumo.setOrdenMantenimiento(getManagedOrden(consumoDetails.getOrdenMantenimiento().getOrdenId()));

        consumoRepuestoRepository.save(consumo);

        // 🚨 CAMBIO CLAVE: Volvemos a buscar la entidad con sus relaciones para devolverla completa
        return consumoRepuestoRepository.findByIdWithRelations(id)
                .orElseThrow(() -> new IllegalStateException("No se pudo encontrar el consumo recién actualizado."));
    }

    @Transactional
    public void deleteConsumoRepuesto(Long id) {
        ConsumoRepuesto consumo = consumoRepuestoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consumo de repuesto no encontrado con id: " + id));

        updateStock(consumo.getRepuesto(), consumo.getCantidad());

        consumoRepuestoRepository.delete(consumo);
    }
}