package com.example.proyectofinal.Controller;

import com.example.proyectofinal.Exception.ResourceNotFoundException;
import com.example.proyectofinal.Model.ConsumoRepuesto;
import com.example.proyectofinal.service.ConsumoRepuestoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/consumos-repuesto")
@CrossOrigin(origins = "http://localhost:3000")
public class ConsumoRepuestoController {

    @Autowired
    private ConsumoRepuestoService consumoRepuestoService;

    @GetMapping
    public List<ConsumoRepuesto> getAllConsumosRepuesto() {
        return consumoRepuestoService.getAllConsumosRepuesto();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsumoRepuesto> getConsumoRepuestoById(@PathVariable Long id) {
        return consumoRepuestoService.getConsumoRepuestoById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createConsumoRepuesto(@Valid @RequestBody ConsumoRepuesto consumoRepuesto) {
        try {
            ConsumoRepuesto savedConsumo = consumoRepuestoService.saveConsumoRepuesto(consumoRepuesto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedConsumo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsumoRepuesto> updateConsumoRepuesto(@PathVariable Long id, @Valid @RequestBody ConsumoRepuesto consumoRepuestoDetails) {
        try {
            ConsumoRepuesto updatedConsumo = consumoRepuestoService.updateConsumoRepuesto(id, consumoRepuestoDetails);
            return ResponseEntity.ok(updatedConsumo);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteConsumoRepuesto(@PathVariable Long id) {
        try {
            consumoRepuestoService.deleteConsumoRepuesto(id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("eliminado", Boolean.TRUE);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
