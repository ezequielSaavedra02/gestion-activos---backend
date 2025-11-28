package com.example.proyectofinal.Repository;

import com.example.proyectofinal.Model.Repuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepuestoRepository extends JpaRepository<Repuesto, Long> {
}
