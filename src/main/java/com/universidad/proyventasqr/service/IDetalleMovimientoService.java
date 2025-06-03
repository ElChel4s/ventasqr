package com.universidad.proyventasqr.service;

import java.util.List;

import com.universidad.proyventasqr.dto.ProductoDTO;

public interface IDetalleMovimientoService {

    List<ProductoDTO> obtenerProductosPorMovimiento(Long idMovimiento);
    // Movimiento obtener
    
}
