package com.universidad.proyventasqr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    public List<MovimientoDTO> obtenerTodosLosMovimientos(){
        return service.obtenerTodosLosMovimientos();
    }
}
