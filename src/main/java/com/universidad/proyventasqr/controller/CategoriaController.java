package com.universidad.proyventasqr.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.proyventasqr.dto.CategoriaDTO;
import com.universidad.proyventasqr.service.ICategoriaService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final ICategoriaService categoriaService;
    
    public CategoriaController(ICategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> obtenerTodasLasCategorias() {
        List<CategoriaDTO> categorias = categoriaService.obtenerTodasLasCategorias();
        return ResponseEntity.ok(categorias);
    }
    
    @PostMapping
    public ResponseEntity<CategoriaDTO> crearCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        CategoriaDTO nuevaCategoria = categoriaService.crearCategoria(categoriaDTO);
        return ResponseEntity.status(201).body(nuevaCategoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> actualizarCategoria(@PathVariable Long id, @RequestBody CategoriaDTO categoriaDTO) {
        CategoriaDTO categoriaActualizado = categoriaService.actualizarCategoria(id, categoriaDTO);
        return ResponseEntity.ok(categoriaActualizado);
    }

    @PutMapping("/{id}/baja")
    public ResponseEntity<CategoriaDTO> eliminarCategoria(
        @PathVariable Long id,
        @RequestBody CategoriaDTO categoriaDTO) {
        CategoriaDTO categoriaEliminada = categoriaService.eliminarCategoria(id, categoriaDTO);
        return ResponseEntity.ok(categoriaEliminada);
    }
    
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<CategoriaDTO> obtenerCategoriaPorNombre(
        @PathVariable String nombre) {
        CategoriaDTO categoria = categoriaService.obtenerCategoriaPorNombre(nombre);
        return ResponseEntity.ok(categoria);
    } 

    @GetMapping("/ascendente")
    public ResponseEntity<List<CategoriaDTO>> obtenerTodasLasCategoriasAscendentemente() {
        List<CategoriaDTO> categorias = categoriaService.obtenerCategoriaAsc();
        return ResponseEntity.ok(categorias);
    }
}