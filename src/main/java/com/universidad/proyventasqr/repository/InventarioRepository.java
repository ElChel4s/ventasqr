package com.universidad.proyventasqr.repository;

import com.universidad.proyventasqr.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Integer> {
    List<Inventario> findByAlmacen_IdAlm(Long almacenId);
    List<Inventario> findByProductoId(Integer productoId);
}
