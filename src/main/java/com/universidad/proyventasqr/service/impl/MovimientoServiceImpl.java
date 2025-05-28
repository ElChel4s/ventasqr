package com.universidad.proyventasqr.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidad.proyventasqr.dto.MovimientoDTO;
import com.universidad.proyventasqr.model.Movimiento;
import com.universidad.proyventasqr.repository.MovimientoRepository;
import com.universidad.proyventasqr.service.IMovimientoService;

@Service
public class MovimientoServiceImpl implements IMovimientoService{
    @Autowired
    private MovimientoRepository repo;
    @Autowired
    private AlmacenServiceImpl alm;

    @Override
    public List<MovimientoDTO> obtenerTodosLosMovimientos() {
        return repo.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    private MovimientoDTO convertToDTO(Movimiento movimiento){
        return MovimientoDTO.builder()
        .idMov(movimiento.getIdMov())
        .tipoMov(movimiento.getTipoMov())
        .fecha(movimiento.getFecha())
        .usuarioMov(movimiento.getUsuarioMov())
        .estado(movimiento.getEstado())
        .almacen(alm.convertToDTO(movimiento.getAlmacen()))
        .build();
    }
    
}
