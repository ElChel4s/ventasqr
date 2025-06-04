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

    /**
     * Eliminar lógico de inventario (no borrar, solo marcar)
     */
    @DeleteMapping("/logico/{id}")
    public ResponseEntity<Void> eliminarLogico(@PathVariable Integer id) {
        inventarioService.eliminarLogico(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Listar inventarios ordenados por cantidad ascendente
     */
    @GetMapping("/ordenar/cantidad")
    public ResponseEntity<List<InventarioDTO>> listarOrdenadosPorCantidad(@RequestParam(defaultValue = "asc") String orden) {
        List<InventarioDTO> inventarios = inventarioService.obtenerTodosLosInventarios();
        inventarios.sort((a, b) -> {
            if (orden.equalsIgnoreCase("desc")) {
                return b.getCantidad().compareTo(a.getCantidad());
            } else {
                return a.getCantidad().compareTo(b.getCantidad());
            }
        });
        return ResponseEntity.ok(inventarios);
    }

    /**
     * Listar inventarios ordenados por nombre ascendente o descendente
     */
    @GetMapping("/ordenar/nombre")
    public ResponseEntity<List<InventarioDTO>> listarOrdenadosPorNombre(@RequestParam(defaultValue = "asc") String orden) {
        List<InventarioDTO> inventarios = inventarioService.obtenerTodosLosInventarios();
        inventarios.sort((a, b) -> {
            String nombreA = a.getProductoId() != null ? a.getProductoId().toString() : "";
            String nombreB = b.getProductoId() != null ? b.getProductoId().toString() : "";
            if (orden.equalsIgnoreCase("desc")) {
                return nombreB.compareToIgnoreCase(nombreA);
            } else {
                return nombreA.compareToIgnoreCase(nombreB);
            }
        });
        return ResponseEntity.ok(inventarios);
    }

    /**
     * Eliminar todos los inventarios de un almacén por su ID
     */
    @DeleteMapping("/almacen/{almacenId}")
    public ResponseEntity<Void> eliminarInventariosPorAlmacen(@PathVariable Long almacenId) {
        List<InventarioDTO> inventarios = inventarioService.obtenerInventariosPorAlmacen(almacenId);
        for (InventarioDTO inv : inventarios) {
            inventarioService.eliminarInventario(inv.getId());
        }
        return ResponseEntity.noContent().build();
    }
}
