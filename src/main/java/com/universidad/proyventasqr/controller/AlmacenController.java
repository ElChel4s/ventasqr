package com.universidad.proyventasqr.controller;

import com.universidad.proyventasqr.model.Almacen;
import com.universidad.proyventasqr.service.AlmacenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/almacenes")
public class AlmacenController {

    @Autowired
    private AlmacenService almacenService;

    @GetMapping
    public List<Almacen> getAllAlmacenes() {
        return almacenService.getAllAlmacenes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Almacen> getAlmacenById(@PathVariable Long id) {
        Optional<Almacen> almacen = almacenService.getAlmacenById(id);
        return almacen.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Almacen createAlmacen(@RequestBody Almacen almacen) {
        return almacenService.saveAlmacen(almacen);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Almacen> updateAlmacen(@PathVariable Long id, @RequestBody Almacen almacenDetails) {
        Almacen updated = almacenService.updateAlmacen(id, almacenDetails);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlmacen(@PathVariable Long id) {
        almacenService.deleteAlmacen(id);
        return ResponseEntity.noContent().build();
    }
}
