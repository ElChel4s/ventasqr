package com.universidad.proyventasqr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.proyventasqr.dto.DetalleMovimientoDTO;
// import com.universidad.proyventasqr.dto.ProductoDTO;
import com.universidad.proyventasqr.service.IDetalleMovimientoService;

@RestController
@RequestMapping("/api/detalle")
public class DetalleMovimientoController {
    @Autowired
    private IDetalleMovimientoService detalleService;

    @GetMapping("/{movId}")
    public ResponseEntity<List<DetalleMovimientoDTO>> obtenerDetallePorMovimiento(@PathVariable Long movId){
        List<DetalleMovimientoDTO> detalles = detalleService.obtenerDetallePorMovimiento(movId);
        return ResponseEntity.ok(detalles);
    }
}
