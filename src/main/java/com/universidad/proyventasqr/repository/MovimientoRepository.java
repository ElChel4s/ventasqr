package com.universidad.proyventasqr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.universidad.proyventasqr.model.Movimiento;
import java.util.List;


@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long>{
    List<Movimiento> findByAlmacenId(Long almId);
}
