package com.example.proyectofinal.Controller;

import com.example.proyectofinal.Model.Repuesto;
import com.example.proyectofinal.service.RepuestoService;
import com.example.proyectofinal.Exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/repuestos")
@CrossOrigin(origins = "http://localhost:3000")
public class RepuestoController {

    private final RepuestoService repuestoService;

    @Autowired
    public RepuestoController(RepuestoService repuestoService) {
        this.repuestoService = repuestoService;
    }

    @GetMapping
    public List<Repuesto> getAllRepuestos() {
        return repuestoService.findAllRepuestos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Repuesto> getRepuestoById(@PathVariable Long id) {
        return repuestoService.findRepuestoById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createRepuesto(@Valid @RequestBody Repuesto repuesto) {
        try {
            Repuesto savedRepuesto = repuestoService.saveRepuesto(repuesto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedRepuesto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Repuesto> updateRepuesto(@PathVariable Long id, @Valid @RequestBody Repuesto repuestoDetails) {
        try {
            Repuesto updatedRepuesto = repuestoService.updateRepuesto(id, repuestoDetails);
            return ResponseEntity.ok(updatedRepuesto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteRepuesto(@PathVariable Long id) {
        try {
            repuestoService.deleteRepuesto(id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("eliminado", Boolean.TRUE);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
