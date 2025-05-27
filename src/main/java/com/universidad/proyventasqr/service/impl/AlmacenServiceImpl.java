package com.universidad.proyventasqr.service;

import com.universidad.proyventasqr.model.Almacen;
import com.universidad.proyventasqr.repository.AlmacenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlmacenServiceImpl implements AlmacenService {

    @Autowired
    private AlmacenRepository almacenRepository;

    @Override
    public List<Almacen> getAllAlmacenes() {
        return almacenRepository.findAll();
    }

    @Override
    public Optional<Almacen> getAlmacenById(Long id) {
        return almacenRepository.findById(id);
    }

    @Override
    public Almacen saveAlmacen(Almacen almacen) {
        return almacenRepository.save(almacen);
    }

    @Override
    public Almacen updateAlmacen(Long id, Almacen almacen) {
        Optional<Almacen> almacenExistente = almacenRepository.findById(id);
        if (almacenExistente.isPresent()) {
            Almacen a = almacenExistente.get();
            a.setNombre(almacen.getNombre());
            a.setUbicacion(almacen.getUbicacion());
            a.setCapacidad(almacen.getCapacidad());
            a.setEstado(almacen.getEstado());
            return almacenRepository.save(a);
        } else {
            return null; // O lanzar excepción, según lo que decidas
        }
    }

    @Override
    public void deleteAlmacen(Long id) {
        almacenRepository.deleteById(id);
    }
}
