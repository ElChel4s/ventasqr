package com.universidad.proyventasqr.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody ProductoDTO productoDTO) {
        ProductoDTO guardarProd = service.crearProducto(productoDTO);
        return new ResponseEntity<>(guardarProd, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerProductoPorId(@PathVariable Long id) {
        ProductoDTO productoDTO = service.obtenerProductoPorId(id);
        return ResponseEntity.ok(productoDTO);
    }

    @GetMapping("/categoria/{catId}")
    public ResponseEntity<List<ProductoDTO>> obtenerProductosPorCategoria(@PathVariable Long catId) {
        List<ProductoDTO> productos = service.obtenerProductosPorCategoria(catId);
        return ResponseEntity.ok(productos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizarProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
        ProductoDTO updatedProducto = service.actualizarProducto(id, productoDTO);
        return ResponseEntity.ok(updatedProducto);
    }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
    //     service.eliminarProducto(id);
    //     return ResponseEntity.noContent().build();
    // }
    //Eliminacion logica "inactivo"
    @PutMapping("/del/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        service.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

}
