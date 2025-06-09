package com.universidad.proyventasqr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.universidad.proyventasqr.model.DetalleMovimiento;

import java.util.List;

@Repository
public interface DetalleMovimientoRepository extends JpaRepository<DetalleMovimiento, Long> {
    List<DetalleMovimiento> findByMovimientoId(Long movId);

}
