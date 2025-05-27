package com.universidad.proyventasqr.service;

import com.universidad.proyventasqr.model.Almacen;
import java.util.List;
import java.util.Optional;

public interface AlmacenService {
    List<Almacen> getAllAlmacenes();
    Optional<Almacen> getAlmacenById(Long id);
    Almacen saveAlmacen(Almacen almacen);
    Almacen updateAlmacen(Long id, Almacen almacen);
    void deleteAlmacen(Long id);
}
