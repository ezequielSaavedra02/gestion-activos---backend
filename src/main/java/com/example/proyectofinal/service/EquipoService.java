package com.example.proyectofinal.service;

import com.example.proyectofinal.Model.Equipo;
import com.example.proyectofinal.Exception.ResourceNotFoundException;
import com.example.proyectofinal.Repository.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class EquipoService {

    private final EquipoRepository equipoRepository;

    @Autowired
    public EquipoService(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;
    }

    public List<Equipo> findAllEquipos() {
        return equipoRepository.findAll();
    }

    public Optional<Equipo> findEquipoById(Long id) {
        return equipoRepository.findById(id);
    }

    @Transactional
    public Equipo saveEquipo(Equipo equipo) {
        // Validación 1: Campos obligatorios no deben ser nulos o vacíos
        if (!StringUtils.hasText(equipo.getMarca())) {
            throw new IllegalArgumentException("La marca del equipo es obligatoria.");
        }
        if (!StringUtils.hasText(equipo.getNumeroSerie())) {
            throw new IllegalArgumentException("El número de serie del equipo es obligatorio.");
        }

        // Validación 2: El número de serie debe ser único
        equipoRepository.findByNumeroSerie(equipo.getNumeroSerie()).ifPresent(e -> {
            throw new IllegalArgumentException("Ya existe un equipo con el número de serie: " + equipo.getNumeroSerie());
        });

        // Si todas las validaciones pasan, guardamos el equipo
        return equipoRepository.save(equipo);
    }

    @Transactional
    public Equipo updateEquipo(Long id, Equipo equipoDetails) {
        Equipo equipo = equipoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el equipo con ID: " + id));

        // Validar que el nuevo número de serie (si cambia) no exista en otro equipo
        if (StringUtils.hasText(equipoDetails.getNumeroSerie()) && !equipoDetails.getNumeroSerie().equals(equipo.getNumeroSerie())) {
            equipoRepository.findByNumeroSerie(equipoDetails.getNumeroSerie()).ifPresent(e -> {
                throw new IllegalArgumentException("El nuevo número de serie ya está en uso por otro equipo.");
            });
        }

        equipo.setMarca(equipoDetails.getMarca());
        equipo.setUbicacion(equipoDetails.getUbicacion());
        equipo.setEstado(equipoDetails.getEstado());
        equipo.setNumeroSerie(equipoDetails.getNumeroSerie());
        equipo.setFechaIngreso(equipoDetails.getFechaIngreso());

        return equipoRepository.save(equipo);
    }

    public void deleteEquipo(Long id) {
        if (equipoRepository.existsById(id)) {
            equipoRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("No se encontró el equipo con ID: " + id);
        }
    }
}
