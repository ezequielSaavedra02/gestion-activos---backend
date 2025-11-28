package com.example.proyectofinal.Repository;

import com.example.proyectofinal.Model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    Optional<Equipo> findByNumeroSerie(String numeroSerie);
}
