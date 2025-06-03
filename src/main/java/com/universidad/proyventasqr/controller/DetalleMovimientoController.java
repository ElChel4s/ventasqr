package com.universidad.proyventasqr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.proyventasqr.dto.ProductoDTO;
import com.universidad.proyventasqr.service.IDetalleMovimientoService;

@RestController
@RequestMapping("/api/movimiento")
public class DetalleMovimientoController {
    @Autowired
    private IDetalleMovimientoService detalleService;

    @GetMapping("/{id}")
    public ResponseEntity<List<ProductoDTO>> obtenerProductosPorMovimiento(
            @PathVariable Long id) {

        List<ProductoDTO> productos = detalleService.obtenerProductosPorMovimiento(id);

        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productos);
    }
}
