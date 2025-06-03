package com.universidad.proyventasqr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.universidad.proyventasqr.model.Almacen;

@Repository
public interface AlmacenRepository extends JpaRepository<Almacen, Long> {
}