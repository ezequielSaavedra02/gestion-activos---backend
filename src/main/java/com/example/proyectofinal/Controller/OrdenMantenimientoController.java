package com.example.proyectofinal.Controller;

import com.example.proyectofinal.Exception.ResourceNotFoundException;
import com.example.proyectofinal.Model.OrdenMantenimiento;
import com.example.proyectofinal.service.OrdenMantenimientoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ordenes-mantenimiento")
@CrossOrigin(origins = "http://localhost:3000")
public class OrdenMantenimientoController {

    @Autowired
    private OrdenMantenimientoService ordenMantenimientoService;

    @GetMapping
    public List<OrdenMantenimiento> getAllOrdenesMantenimiento() {
        return ordenMantenimientoService.getAllOrdenesMantenimiento();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenMantenimiento> getOrdenMantenimientoById(@PathVariable Long id) {
        return ordenMantenimientoService.getOrdenMantenimientoById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createOrdenMantenimiento(@Valid @RequestBody OrdenMantenimiento ordenMantenimiento) {
        try {
            OrdenMantenimiento savedOrden = ordenMantenimientoService.saveOrdenMantenimiento(ordenMantenimiento);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedOrden);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdenMantenimiento> updateOrdenMantenimiento(@PathVariable Long id, @Valid @RequestBody OrdenMantenimiento ordenMantenimientoDetails) {
        try {
            OrdenMantenimiento updatedOrden = ordenMantenimientoService.updateOrdenMantenimiento(id, ordenMantenimientoDetails);
            return ResponseEntity.ok(updatedOrden);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteOrdenMantenimiento(@PathVariable Long id) {
        try {
            ordenMantenimientoService.deleteOrdenMantenimiento(id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("eliminado", Boolean.TRUE);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
