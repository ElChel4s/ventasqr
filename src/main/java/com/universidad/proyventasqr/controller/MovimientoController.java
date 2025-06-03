package com.universidad.proyventasqr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.proyventasqr.dto.MovimientoDTO;
import com.universidad.proyventasqr.service.IMovimientoService;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {
    @Autowired
    private IMovimientoService service;
    @GetMapping
    public ResponseEntity<List<MovimientoDTO>> obtenerTodosLosMovimientos(){
        List<MovimientoDTO> movimientos = service.obtenerTodosLosMovimientos();
        return ResponseEntity.ok(movimientos);
    }
    @PostMapping
    public ResponseEntity<MovimientoDTO> crearMovimiento(@RequestBody MovimientoDTO movimientoDTO){
        MovimientoDTO guardarMov = service.crearMovimiento(movimientoDTO);
        return new ResponseEntity<>(guardarMov,HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<MovimientoDTO> obtenerMovimientoPorId(@PathVariable Long id){
        MovimientoDTO movimientoDTO = service.obtenerMovimientoPorId(id);
        return ResponseEntity.ok(movimientoDTO);
    }
    @GetMapping("/almacen/{almId}")
    public ResponseEntity<List<MovimientoDTO>> obtenerMovimientosPorAlmacen(@PathVariable Long almId){
        List<MovimientoDTO> movimientos = service.obtenerMovimientosPorAlmacen(almId);
        return ResponseEntity.ok(movimientos);
    }
    @PutMapping("/{id}")
    public ResponseEntity<MovimientoDTO> actualizarMovimiento(@PathVariable Long id, @RequestBody MovimientoDTO movimientoDTO){
        MovimientoDTO updateMov = service.actualizarMovimiento(id, movimientoDTO);
        return ResponseEntity.ok(updateMov);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMovimiento(@PathVariable Long id){
        service.eliminarMovimiento(id);
        return ResponseEntity.noContent().build();
    }
}
