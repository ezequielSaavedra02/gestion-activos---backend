package com.example.proyectofinal.Repository;

import com.example.proyectofinal.Model.OrdenMantenimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface OrdenMantenimientoRepository extends JpaRepository<OrdenMantenimiento, Long> {

    // Añade esta consulta JPQL: Fuerza a JPA a cargar el objeto 'equipo'
    // en la misma consulta que la orden.
    @Query("SELECT o FROM OrdenMantenimiento o JOIN FETCH o.equipo")
    List<OrdenMantenimiento> findAllWithEquipo();
}