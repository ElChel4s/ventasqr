package com.universidad.proyventasqr.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidad.proyventasqr.dto.MovimientoDTO;
import com.universidad.proyventasqr.model.Almacen;
import com.universidad.proyventasqr.model.Movimiento;
import com.universidad.proyventasqr.repository.AlmacenRepository;
import com.universidad.proyventasqr.repository.MovimientoRepository;
import com.universidad.proyventasqr.service.IMovimientoService;

import jakarta.transaction.Transactional;

@Service
public class MovimientoServiceImpl implements IMovimientoService{
    @Autowired
    private MovimientoRepository movimientoRepository;
    @Autowired
    private AlmacenRepository almacenRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<MovimientoDTO> obtenerTodosLosMovimientos() {
        return  movimientoRepository.findAll().stream().map(
            movimiento -> modelMapper.map(movimiento, MovimientoDTO.class)
        ).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MovimientoDTO crearMovimiento(MovimientoDTO movimientoDTO) {
        if (movimientoDTO.getAlmacen() == null || movimientoDTO.getAlmacen().getId() == null){
            throw new IllegalArgumentException("Debe ingresar un almacen valido");
        }
        Almacen almacen = almacenRepository.findById(movimientoDTO.getAlmacen().getId()).orElseThrow(()->new RuntimeException("El almacen con ID: "+ movimientoDTO.getAlmacen().getId()+" no existe"));
        Movimiento movimiento = modelMapper.map(movimientoDTO, Movimiento.class);
        movimiento.setAlmacen(almacen);
        Movimiento guardarMovimiento = movimientoRepository.save(movimiento);
        return modelMapper.map(guardarMovimiento, MovimientoDTO.class);
        
    }

    @Override
    public MovimientoDTO obtenerMovimientoPorId(Long id) {
        Movimiento movimiento = movimientoRepository.findById(id).orElseThrow(()->new RuntimeException("El movimiento con ID: "+id+" no existe"));
        return modelMapper.map(movimiento, MovimientoDTO.class);
    }

    @Override
    @Transactional
    public List<MovimientoDTO> obtenerMovimientosPorAlmacen(Long almId) {
        return movimientoRepository.findByAlmacenId(almId).stream().map(movimiento->modelMapper.map(movimiento, MovimientoDTO.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MovimientoDTO actualizarMovimiento(Long id, MovimientoDTO movimientoDTO) {
        Movimiento movimiento = movimientoRepository.findById(id).orElseThrow(()->new RuntimeException("El movimiento con ID: "+id+" no existe"));
        //verificar si se esta cambiando de almacen
        if(!movimiento.getAlmacen().getId().equals(movimientoDTO.getAlmacen().getId())){
            Almacen nuevoAlmacen = almacenRepository.findById(movimientoDTO.getAlmacen().getId()).orElseThrow(()->new RuntimeException("El almacen con ID: "+movimientoDTO.getAlmacen().getId()+" no existe"));
            movimiento.setAlmacen(nuevoAlmacen);
        }
        movimiento.setTipoMov(movimientoDTO.getTipoMov());
        movimiento.setFecha(movimientoDTO.getFecha());
        movimiento.setUsuarioMov(movimientoDTO.getUsuarioMov());
        movimiento.setEstado(movimientoDTO.getEstado());
        Movimiento updateMov = movimientoRepository.save(movimiento);
        return modelMapper.map(updateMov, MovimientoDTO.class);
    }

    @Override
    @Transactional
    public void eliminarMovimiento(Long id) {
        Movimiento movimiento = movimientoRepository.findById(id).orElseThrow(()->new RuntimeException("El movimiento con ID: "+id+" no existe"));
        movimientoRepository.delete(movimiento);
    }
    
}
