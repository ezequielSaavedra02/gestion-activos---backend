package com.example.proyectofinal.service;

import com.example.proyectofinal.Exception.ResourceNotFoundException;
import com.example.proyectofinal.Model.OrdenMantenimiento;
import com.example.proyectofinal.Model.Equipo; // 👈 NECESARIO: Importar Modelo de Equipo
import com.example.proyectofinal.Repository.OrdenMantenimientoRepository;
import com.example.proyectofinal.Repository.EquipoRepository; // 👈 NECESARIO: Importar Repositorio de Equipo
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdenMantenimientoService {

    @Autowired
    private OrdenMantenimientoRepository ordenMantenimientoRepository;

    @Autowired
    private EquipoRepository equipoRepository; // 👈 INYECTADO: Necesario para buscar la entidad Equipo


    /**
     * Helper para buscar la entidad Equipo gestionada por JPA.
     * @throws ResourceNotFoundException si el equipo no existe.
     * @throws IllegalArgumentException si el ID es nulo.
     */
    private Equipo getManagedEquipo(Long equipoId) {
        if (equipoId == null) {
            throw new IllegalArgumentException("El ID del equipo no puede ser nulo.");
        }
        return equipoRepository.findById(equipoId)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo no encontrado con id: " + equipoId));
    }


    // Modificado para usar JOIN FETCH
    public List<OrdenMantenimiento> getAllOrdenesMantenimiento() {
        // Asumiendo que has implementado findAllWithEquipo() en el Repositorio
        return ordenMantenimientoRepository.findAllWithEquipo();
    }

    public Optional<OrdenMantenimiento> getOrdenMantenimientoById(Long id) {
        return ordenMantenimientoRepository.findById(id);
    }

    // 🟢 CREAR: Busca el Equipo y lo asocia antes de guardar
    public OrdenMantenimiento saveOrdenMantenimiento(OrdenMantenimiento ordenMantenimiento) {
        // Aseguramos que la orden trae un objeto Equipo con ID
        if (ordenMantenimiento.getEquipo() == null) {
            throw new IllegalArgumentException("Debe seleccionar un equipo para la orden de mantenimiento.");
        }

        // 1. Obtener el ID del equipo (incluso si solo es un proxy del frontend)
        Long equipoId = ordenMantenimiento.getEquipo().getEquipoId();

        // 2. Buscar el Equipo completo y gestionado por JPA
        Equipo equipoManaged = getManagedEquipo(equipoId);

        // 3. Asignar el Equipo completo a la orden
        ordenMantenimiento.setEquipo(equipoManaged);

        return ordenMantenimientoRepository.save(ordenMantenimiento);
    }

    // 🟢 ACTUALIZAR: Busca el Equipo y lo asocia si cambia
    public OrdenMantenimiento updateOrdenMantenimiento(Long id, OrdenMantenimiento ordenDetails) {
        OrdenMantenimiento orden = ordenMantenimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Orden de mantenimiento no encontrada con id: " + id));

        orden.setFecha(ordenDetails.getFecha());
        orden.setMotivo(ordenDetails.getMotivo());
        orden.setDescripcion(ordenDetails.getDescripcion());

        // Lógica para actualizar el Equipo solo si se proporcionó un ID en los detalles
        if (ordenDetails.getEquipo() != null && ordenDetails.getEquipo().getEquipoId() != null) {
            Long equipoId = ordenDetails.getEquipo().getEquipoId();
            Equipo equipoManaged = getManagedEquipo(equipoId);
            orden.setEquipo(equipoManaged);
        } else {
            // Si la ordenDetails NO trae un Equipo, podría ser un error,
            // pero mantenemos el equipo existente para evitar NullPointer en la BD
            throw new IllegalArgumentException("Debe seleccionar un equipo para la orden de mantenimiento.");
        }

        return ordenMantenimientoRepository.save(orden);
    }

    public void deleteOrdenMantenimiento(Long id) {
        if (!ordenMantenimientoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Orden de mantenimiento no encontrada con id: " + id);
        }
        ordenMantenimientoRepository.deleteById(id);
    }
}
