package com.universidad.proyventasqr.service;

import java.util.List;

import com.universidad.proyventasqr.dto.MovimientoDTO;

public interface IMovimientoService {
    List<MovimientoDTO> obtenerTodosLosMovimientos();
}
