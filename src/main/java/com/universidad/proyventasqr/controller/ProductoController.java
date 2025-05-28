package com.universidad.proyventasqr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.proyventasqr.dto.ProductoDTO;
import com.universidad.proyventasqr.service.IProductoService;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    @Autowired
    private IProductoService service;
    @GetMapping
    public List<ProductoDTO> obtenerTodosLosProductos(){
        return service.obtenerTodosLosProductos();
    }
}
