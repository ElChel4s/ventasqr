package com.universidad.proyventasqr.service.impl;

import com.universidad.proyventasqr.dto.AlmacenDTO;
import com.universidad.proyventasqr.model.Almacen;
import com.universidad.proyventasqr.repository.AlmacenRepository;
import com.universidad.proyventasqr.service.IAlmacenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlmacenServiceImpl implements IAlmacenService {

    private final AlmacenRepository almacenRepository;

    @Autowired
    public AlmacenServiceImpl(AlmacenRepository almacenRepository) {
        this.almacenRepository = almacenRepository;
    }

    @Override
    public List<AlmacenDTO> obtenerTodosLosAlmacenes() {
        return almacenRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AlmacenDTO crearAlmacen(AlmacenDTO almacenDTO) {
        Almacen almacen = convertToEntity(almacenDTO);
        Almacen almacenGuardado = almacenRepository.save(almacen);
        return convertToDTO(almacenGuardado);
    }

    @Override
    public AlmacenDTO actualizarAlmacen(Long id, AlmacenDTO almacenDTO) {
        Almacen almacenExistente = almacenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Almacén no encontrado"));
        almacenExistente.setNombre(almacenDTO.getNombre());
        almacenExistente.setUbicacion(almacenDTO.getUbicacion());
        almacenExistente.setCapacidad(almacenDTO.getCapacidad());
        almacenExistente.setEstado(almacenDTO.getEstado());

        Almacen almacenActualizado = almacenRepository.save(almacenExistente);
        return convertToDTO(almacenActualizado);
    }

    @Override
    public void eliminarLogico(Long id) {
        Almacen almacen = almacenRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Almacén no encontrado"));
        almacen.setEstado("eliminado");
        almacenRepository.save(almacen);
    }

    @Override
    public void bajaAlmacen(Long id, String motivoBaja) {
        Almacen almacen = almacenRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Almacén no encontrado"));
        almacen.setEstado("baja: " + motivoBaja);
        almacenRepository.save(almacen);
    }

    @Override
    public List<AlmacenDTO> obtenerAlmacenesPorEstado(String estado) {
        return almacenRepository.findByEstado(estado).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarAlmacen(Long id) {
        almacenRepository.deleteById(id);
    }

    // Método auxiliar para convertir entidad a DTO
    private AlmacenDTO convertToDTO(Almacen almacen) {
        return AlmacenDTO.builder()
                .idAlm(almacen.getIdAlm())
                .nombre(almacen.getNombre())
                .ubicacion(almacen.getUbicacion())
                .capacidad(almacen.getCapacidad())
                .estado(almacen.getEstado())
                .build();
    }

    // Método auxiliar para convertir DTO a entidad
    private Almacen convertToEntity(AlmacenDTO almacenDTO) {
        return Almacen.builder()
                .idAlm(almacenDTO.getIdAlm())
                .nombre(almacenDTO.getNombre())
                .ubicacion(almacenDTO.getUbicacion())
                .capacidad(almacenDTO.getCapacidad())
                .estado(almacenDTO.getEstado())
                .build();
    }
}
