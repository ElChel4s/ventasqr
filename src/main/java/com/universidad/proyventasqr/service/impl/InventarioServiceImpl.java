package com.universidad.proyventasqr.service.impl;

import com.universidad.proyventasqr.dto.InventarioDTO;
import com.universidad.proyventasqr.model.Inventario;
import com.universidad.proyventasqr.repository.InventarioRepository;
import com.universidad.proyventasqr.service.IInventarioService;
import com.universidad.proyventasqr.repository.AlmacenRepository;
import com.universidad.proyventasqr.model.Almacen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventarioServiceImpl implements IInventarioService {
    private final InventarioRepository inventarioRepository;
    private final AlmacenRepository almacenRepository;

    @Autowired
    public InventarioServiceImpl(InventarioRepository inventarioRepository, AlmacenRepository almacenRepository) {
        this.inventarioRepository = inventarioRepository;
        this.almacenRepository = almacenRepository;
    }

    @Override
    public List<InventarioDTO> obtenerTodosLosInventarios() {
        return inventarioRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InventarioDTO crearInventario(InventarioDTO inventarioDTO) {
        Almacen almacen = almacenRepository.findById(inventarioDTO.getAlmacenId())
                .orElseThrow(() -> new RuntimeException("Almacén no encontrado"));
        Inventario inventario = convertToEntity(inventarioDTO, almacen);
        Inventario guardado = inventarioRepository.save(inventario);
        return convertToDTO(guardado);
    }

    @Override
    public InventarioDTO actualizarInventario(Integer id, InventarioDTO inventarioDTO) {
        Inventario existente = inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
        Almacen almacen = almacenRepository.findById(inventarioDTO.getAlmacenId())
                .orElseThrow(() -> new RuntimeException("Almacén no encontrado"));
        existente.setProductoId(inventarioDTO.getProductoId());
        existente.setAlmacen(almacen);
        existente.setCantidad(inventarioDTO.getCantidad());
        existente.setActualizadoEn(inventarioDTO.getActualizadoEn());
        Inventario actualizado = inventarioRepository.save(existente);
        return convertToDTO(actualizado);
    }

    @Override
    public void eliminarInventario(Integer id) {
        inventarioRepository.deleteById(id);
    }

    @Override
    public List<InventarioDTO> obtenerInventariosPorAlmacen(Long almacenId) {
        return inventarioRepository.findByAlmacen_IdAlm(almacenId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InventarioDTO> obtenerInventariosPorProducto(Integer productoId) {
        return inventarioRepository.findByProductoId(productoId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private InventarioDTO convertToDTO(Inventario inventario) {
        return InventarioDTO.builder()
                .id(inventario.getId())
                .productoId(inventario.getProductoId())
                .almacenId(inventario.getAlmacen() != null ? inventario.getAlmacen().getIdAlm() : null)
                .cantidad(inventario.getCantidad())
                .actualizadoEn(inventario.getActualizadoEn())
                .build();
    }

    private Inventario convertToEntity(InventarioDTO dto, Almacen almacen) {
        return Inventario.builder()
                .id(dto.getId())
                .productoId(dto.getProductoId())
                .almacen(almacen)
                .cantidad(dto.getCantidad())
                .actualizadoEn(dto.getActualizadoEn())
                .build();
    }
}
