package com.example.proyectofinal.Controller;

import com.example.proyectofinal.Model.Equipo;
import com.example.proyectofinal.Exception.ResourceNotFoundException;
import com.example.proyectofinal.service.EquipoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/equipos")
@CrossOrigin(origins = "http://localhost:3000")
public class EquipoController {

    private final EquipoService equipoService;

    @Autowired
    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @GetMapping
    public List<Equipo> getAllEquipos() {
        return equipoService.findAllEquipos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipo> getEquipoById(@PathVariable Long id) {
        return equipoService.findEquipoById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createEquipo(@Valid @RequestBody Equipo equipo) {
        try {
            Equipo savedEquipo = equipoService.saveEquipo(equipo);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEquipo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipo> updateEquipo(@PathVariable Long id, @Valid @RequestBody Equipo equipoDetails) {
        try {
            Equipo updatedEquipo = equipoService.updateEquipo(id, equipoDetails);
            return ResponseEntity.ok(updatedEquipo);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEquipo(@PathVariable Long id) {
        try {
            equipoService.deleteEquipo(id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("eliminado", Boolean.TRUE);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
