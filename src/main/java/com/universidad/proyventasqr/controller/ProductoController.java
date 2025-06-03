package com.universidad.proyventasqr.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<List<ProductoDTO>> obtenerTodosLosProductos() {
        List<ProductoDTO> productos = service.obtenerTodosLosProductos();
        return ResponseEntity.ok(productos);
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody ProductoDTO
    productoDTO){
    ProductoDTO guardarProd = service.crearProducto(productoDTO);
    return new ResponseEntity<>(guardarProd, HttpStatus.CREATED);
    }

}
