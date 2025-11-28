package com.example.proyectofinal.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "repuestos")
public class Repuesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long repuestoId;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "El tipo no puede estar vacío")
    @Column(nullable = false)
    private String tipo;

    @Min(value = 0, message = "La cantidad no puede ser negativa")
    @Column(nullable = false)
    private int cantidad;

    public Repuesto() {
    }

    public Repuesto(Long repuestoId, String nombre, String tipo, int cantidad) {
        this.repuestoId = repuestoId;
        this.nombre = nombre;
        this.tipo = tipo;
        this.cantidad = cantidad;
    }

    public Long getRepuestoId() {
        return repuestoId;
    }

    public void setRepuestoId(Long repuestoId) {
        this.repuestoId = repuestoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}
