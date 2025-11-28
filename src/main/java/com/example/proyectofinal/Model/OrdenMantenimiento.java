package com.example.proyectofinal.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "consumosRepuesto"})
@Entity
@Table(name = "ordenes_mantenimiento")
public class OrdenMantenimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ordenId;

    @NotNull(message = "La fecha no puede ser nula")
    @Column(nullable = false)
    private Date fecha;

    @NotBlank(message = "El motivo no puede estar vacío")
    @Column(nullable = false)
    private String motivo;

    private String descripcion;

    @NotNull(message = "La orden debe estar asociada a un equipo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipo_id", nullable = false)
    private Equipo equipo;

    public OrdenMantenimiento() {
    }

    public OrdenMantenimiento(Long ordenId, Date fecha, String motivo, String descripcion, Equipo equipo) {
        this.ordenId = ordenId;
        this.fecha = fecha;
        this.motivo = motivo;
        this.descripcion = descripcion;
        this.equipo = equipo;
    }

    // Getters y Setters

    public Long getOrdenId() {
        return ordenId;
    }

    public void setOrdenId(Long ordenId) {
        this.ordenId = ordenId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }
}
