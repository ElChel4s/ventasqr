package com.universidad.proyventasqr.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "almacen")
public class Almacen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alm")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "ubicacion")
    private String ubicacion;

    @Column(name = "capacidad")
    private Integer capacidad;

    @Column(name = "estado")
    private String estado;

    @JsonIgnore
    @OneToMany(mappedBy = "almacen", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Movimiento> movimientos;


}