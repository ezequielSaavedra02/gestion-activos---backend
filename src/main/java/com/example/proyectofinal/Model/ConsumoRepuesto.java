package com.example.proyectofinal.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // 👈 NECESARIO para Jackson
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

// 🚨 AÑADIDO: Ignorar campos internos de Hibernate y proxies
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "consumos_repuesto")
public class ConsumoRepuesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long consumoId;

    @NotNull(message = "La fecha de entrada no puede ser nula")
    @Column(nullable = false)
    private Date fechaEntrada;

    private Date fechaSalida;

    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    @Column(nullable = false)
    private int cantidad;

    @NotNull(message = "El consumo debe estar asociado a un repuesto")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repuesto_id", nullable = false)
    private Repuesto repuesto;

    @NotNull(message = "El consumo debe estar asociado a una orden de mantenimiento")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orden_id", nullable = false)
    private OrdenMantenimiento ordenMantenimiento;

    public ConsumoRepuesto() {
    }

    public ConsumoRepuesto(Long consumoId, Date fechaEntrada, Date fechaSalida, int cantidad, Repuesto repuesto, OrdenMantenimiento ordenMantenimiento) {
        this.consumoId = consumoId;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.cantidad = cantidad;
        this.repuesto = repuesto;
        this.ordenMantenimiento = ordenMantenimiento;
    }

    // Getters y Setters

    public Long getConsumoId() {
        return consumoId;
    }

    public void setConsumoId(Long consumoId) {
        this.consumoId = consumoId;
    }

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Repuesto getRepuesto() {
        return repuesto;
    }

    public void setRepuesto(Repuesto repuesto) {
        this.repuesto = repuesto;
    }

    public OrdenMantenimiento getOrdenMantenimiento() {
        return ordenMantenimiento;
    }

    public void setOrdenMantenimiento(OrdenMantenimiento ordenMantenimiento) {
        this.ordenMantenimiento = ordenMantenimiento;
    }
}