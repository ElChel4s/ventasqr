package com.universidad.proyventasqr.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.proyventasqr.dto.DetalleMovimientoDTO;
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

    @PostMapping
    public ResponseEntity<DetalleMovimientoDTO> crearDetalle(@RequestBody DetalleMovimientoDTO detalleMovimientoDTO) {
        DetalleMovimientoDTO creado = detalleService.crearDetalleMovimiento(detalleMovimientoDTO);
        return ResponseEntity.ok(creado);
    }

    @PostMapping("/agregar-multiples")
    public ResponseEntity<List<DetalleMovimientoDTO>> agregarMultiplesDetalles(@RequestBody List<DetalleMovimientoDTO> detallesDTO) {
        List<DetalleMovimientoDTO> creados = detallesDTO.stream()
            .map(detalleService::crearDetalleMovimiento)
            .collect(Collectors.toList());
        return ResponseEntity.ok(creados);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleMovimientoDTO> actualizarDetalle(@PathVariable Long id, @RequestBody DetalleMovimientoDTO detalleMovimientoDTO) {
        DetalleMovimientoDTO actualizado = detalleService.actualizarDetalleMovimiento(id, detalleMovimientoDTO);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDetalle(@PathVariable Long id) {
        detalleService.eliminarDetalleMovimiento(id);
        return ResponseEntity.noContent().build();
    }
}
