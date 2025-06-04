package com.universidad.proyventasqr.service.impl;


import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidad.proyventasqr.dto.DetalleMovimientoDTO;
import com.universidad.proyventasqr.repository.DetalleMovimientoRepository;
import com.universidad.proyventasqr.service.IDetalleMovimientoService;

import jakarta.transaction.Transactional;

@Service
public class DetalleMovimientoServiceImpl implements IDetalleMovimientoService{

    @Autowired
    private DetalleMovimientoRepository detalleRepository;
    @Autowired
    private ModelMapper modelMapper;
    DetalleMovimientoServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public DetalleMovimientoDTO crearDetalleMovimiento(DetalleMovimientoDTO detalleMovimientoDTO) {
        return null;
    }
    @Override
    @Transactional
    public List<DetalleMovimientoDTO> obtenerDetallePorMovimiento(Long movId) {
        return detalleRepository.findByMovimientoId(movId).stream().map(detalle -> modelMapper.map(detalle, DetalleMovimientoDTO.class)).collect(Collectors.toList());
    }

}
