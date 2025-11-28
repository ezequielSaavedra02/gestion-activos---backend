package com.example.proyectofinal.Repository;

import com.example.proyectofinal.Model.ConsumoRepuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param; // Asegúrate de importar Param

import java.util.List;
import java.util.Optional; // Y Optional

public interface ConsumoRepuestoRepository extends JpaRepository<ConsumoRepuesto, Long> {

    // Query existente para cargar toda la lista con relaciones
    @Query("SELECT c FROM ConsumoRepuesto c JOIN FETCH c.repuesto r JOIN FETCH c.ordenMantenimiento o")
    List<ConsumoRepuesto> findAllWithRelations();

    // 🚨 NUEVO MÉTODO: Carga un solo consumo con sus relaciones
    @Query("SELECT c FROM ConsumoRepuesto c JOIN FETCH c.repuesto r JOIN FETCH c.ordenMantenimiento o WHERE c.consumoId = :id")
    Optional<ConsumoRepuesto> findByIdWithRelations(@Param("id") Long id);
}