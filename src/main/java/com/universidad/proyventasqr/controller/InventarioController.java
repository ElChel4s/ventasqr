package com.universidad.proyventasqr.controller;

import com.universidad.proyventasqr.dto.InventarioDTO;
import com.universidad.proyventasqr.service.IInventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventarios")
public class InventarioController {
    private final IInventarioService inventarioService;

    @Autowired
    public InventarioController(IInventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @GetMapping
    public ResponseEntity<List<InventarioDTO>> obtenerTodos() {
        return ResponseEntity.ok(inventarioService.obtenerTodosLosInventarios());
    }

    @PostMapping
    public ResponseEntity<InventarioDTO> crear(@RequestBody InventarioDTO inventarioDTO) {
        InventarioDTO nuevo = inventarioService.crearInventario(inventarioDTO);
        return ResponseEntity.status(201).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventarioDTO> actualizar(@PathVariable Integer id,
            @RequestBody InventarioDTO inventarioDTO) {
        InventarioDTO actualizado = inventarioService.actualizarInventario(id, inventarioDTO);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        inventarioService.eliminarInventario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/almacen/{almacenId}")
    public ResponseEntity<List<InventarioDTO>> obtenerPorAlmacen(@PathVariable Long almacenId) {
        return ResponseEntity.ok(inventarioService.obtenerInventariosPorAlmacen(almacenId));
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<InventarioDTO>> obtenerPorProducto(@PathVariable Integer productoId) {
        return ResponseEntity.ok(inventarioService.obtenerInventariosPorProducto(productoId));
    }

    /**
     * Obtener un inventario por su ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<InventarioDTO> obtenerPorId(@PathVariable Integer id) {
        List<InventarioDTO> todos = inventarioService.obtenerTodosLosInventarios();
        return todos.stream()
                .filter(inv -> inv.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
