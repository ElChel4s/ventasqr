package com.universidad.proyventasqr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.universidad.proyventasqr.model.DetalleMovimiento;
import java.util.List;

@Repository
public interface DetalleMovimientoRepository extends JpaRepository<DetalleMovimiento, Long> {
    // Alternativa con JOIN FETCH para evitar problemas de Lazy Loading
    @Query("SELECT dm FROM DetalleMovimiento dm JOIN FETCH dm.producto WHERE dm.movimiento.idMov = :idMovimiento")
    List<DetalleMovimiento> findByMovimientoIdWithProductos(@Param("idMovimiento") Long idMovimiento);

}
