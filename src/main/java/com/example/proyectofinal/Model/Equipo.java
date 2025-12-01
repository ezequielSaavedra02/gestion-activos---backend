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
    
    @NotBlank(message = "La ip no puede estar vacia")
    @Column(nullable = false)
    private String ip;

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

    public Equipo(Long equipoId, String marca, String ip, Date fechaIngreso, String ubicacion, String estado, String numeroSerie) {
        this.equipoId = equipoId;
        this.marca = marca;
        this.ip = ip;
        this.fechaIngreso = fechaIngreso;
        this.ubicacion = ubicacion;
        this.estado = estado;
        this.numeroSerie = numeroSerie;
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
    
    public void setIp(String ip){
        this.ip = ip;
    }
    
    public String getIp(){
        return ip;
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

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

}