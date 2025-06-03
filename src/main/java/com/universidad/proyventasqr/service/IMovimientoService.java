package com.universidad.proyventasqr.service;

import java.util.List;

import com.universidad.proyventasqr.dto.MovimientoDTO;

public interface IMovimientoService {
    MovimientoDTO crearMovimiento(MovimientoDTO movimientoDTO);
    MovimientoDTO obtenerMovimientoPorId(Long id);
    List<MovimientoDTO> obtenerTodosLosMovimientos();
    List<MovimientoDTO> obtenerMovimientosPorAlmacen(Long almId);
    MovimientoDTO actualizarMovimiento(Long id, MovimientoDTO movimientoDTO);
    void eliminarMovimiento(Long id);
}
