package com.universidad.proyventasqr.controller;

import com.universidad.proyventasqr.dto.AlmacenDTO;
import com.universidad.proyventasqr.service.IAlmacenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/almacenes")
public class AlmacenController {

    private final IAlmacenService almacenService;

    @Autowired
    public AlmacenController(IAlmacenService almacenService) {
        this.almacenService = almacenService;
    }

    /**
     * Obtener todos los almacenes.
     * 
     * @return ResponseEntity con lista de almacenes.
     */
    @GetMapping
    public ResponseEntity<List<AlmacenDTO>> obtenerTodosLosAlmacenes() {
        List<AlmacenDTO> almacenes = almacenService.obtenerTodosLosAlmacenes();
        return ResponseEntity.ok(almacenes);
    }

    /**
     * Crear un nuevo almacén.
     * @param almacenDTO El DTO del almacén a crear.
     * @return ResponseEntity con el almacén creado.
     */
    @PostMapping
    public ResponseEntity<AlmacenDTO> crearAlmacen(@RequestBody AlmacenDTO almacenDTO) {
        AlmacenDTO nuevoAlmacen = almacenService.crearAlmacen(almacenDTO);
        return ResponseEntity.status(201).body(nuevoAlmacen);
    }

    /**
     * Actualizar un almacén existente.
     * @param id El ID del almacén a actualizar.
     * @param almacenDTO Los nuevos datos del almacén.
     * @return ResponseEntity con el almacén actualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AlmacenDTO> actualizarAlmacen(@PathVariable Long id, @RequestBody AlmacenDTO almacenDTO) {
        AlmacenDTO almacenActualizado = almacenService.actualizarAlmacen(id, almacenDTO);
        return ResponseEntity.ok(almacenActualizado);
    }

    /**
     * Eliminar un almacén.
     * @param id El ID del almacén a eliminar.
     * @return ResponseEntity vacío con código 204 si es exitoso.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAlmacen(@PathVariable Long id) {
        almacenService.eliminarAlmacen(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtener almacenes filtrados por su estado (activo/inactivo).
     * 
     * @param estado El estado del almacén (activo/inactivo).
     * @return ResponseEntity con lista de almacenes filtrados.
     */
    @GetMapping("/estado")
    public ResponseEntity<List<AlmacenDTO>> obtenerAlmacenesPorEstado(@RequestParam String estado) {
        List<AlmacenDTO> almacenes = almacenService.obtenerAlmacenesPorEstado(estado);
        return ResponseEntity.ok(almacenes);
    }
}
