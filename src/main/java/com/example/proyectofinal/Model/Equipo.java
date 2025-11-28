package com.example.proyectofinal.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // 👈 NECESARIO para Jackson
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

// AÑADIDO: Ignorar campos internos de Hibernate y proxies que causan errores de serialización (el problema de que el equipo sea null en el frontend).
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "equipos")
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long equipoId;

    @NotBlank(message = "La marca no puede estar vacía")
    @Column(nullable = false)
    private String marca;

    @NotNull(message = "La fecha de ingreso no puede ser nula")
    @Column(nullable = false)
    private Date fechaIngreso;

    @NotBlank(message = "La ubicación no puede estar vacía")
    @Column(nullable = false)
    private String ubicacion;

    @NotBlank(message = "El estado no puede estar vacío")
    @Column(nullable = false)
    private String estado;

    @NotBlank(message = "El número de serie no puede estar vacío")
    @Column(nullable = false, unique = true)
    private String numeroSerie;

    public Equipo() {
    }

    public Equipo(Long equipoId, String marca, Date fechaIngreso, String ubicacion, String estado, String nummeroSerie) {
        this.equipoId = equipoId;
        this.marca = marca;
        this.fechaIngreso = fechaIngreso;
        this.ubicacion = ubicacion;
        this.estado = estado;
        this.numeroSerie = nummeroSerie;
    }

    public Long getEquipoId() {
        return equipoId;
    }

    public void setEquipoId(Long equipoId) {
        this.equipoId = equipoId;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String nummeroSerie) {
        this.numeroSerie = nummeroSerie;
    }

}