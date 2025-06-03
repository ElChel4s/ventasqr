package com.universidad.proyventasqr.service;

import java.util.List;

import com.universidad.proyventasqr.dto.DetalleMovimientoDTO;
// import com.universidad.proyventasqr.dto.ProductoDTO;

public interface IDetalleMovimientoService {

    DetalleMovimientoDTO crearDetalleMovimiento(DetalleMovimientoDTO detalleMovimientoDTO);
    List<DetalleMovimientoDTO> obtenerDetallePorMovimiento(Long movId);
    
}
