package com.example.proyectofinal.service;

import com.example.proyectofinal.Model.Repuesto;
import com.example.proyectofinal.Exception.ResourceNotFoundException;
import com.example.proyectofinal.Repository.RepuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RepuestoService {

    private final RepuestoRepository repuestoRepository;

    @Autowired
    public RepuestoService(RepuestoRepository repuestoRepository) {
        this.repuestoRepository = repuestoRepository;
    }

    public List<Repuesto> findAllRepuestos() {
        return repuestoRepository.findAll();
    }

    public Optional<Repuesto> findRepuestoById(Long id) {
        return repuestoRepository.findById(id);
    }

    @Transactional
    public Repuesto saveRepuesto(Repuesto repuesto) {
        return repuestoRepository.save(repuesto);
    }

    @Transactional
    public Repuesto updateRepuesto(Long id, Repuesto repuestoDetails) {
        Repuesto repuesto = repuestoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el repuesto con ID: " + id));

        repuesto.setNombre(repuestoDetails.getNombre());
        repuesto.setTipo(repuestoDetails.getTipo());
        repuesto.setCantidad(repuestoDetails.getCantidad());

        return repuestoRepository.save(repuesto);
    }

    public void deleteRepuesto(Long id) {
        if (!repuestoRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se encontró el repuesto con ID: " + id);
        }
        repuestoRepository.deleteById(id);
    }
}
