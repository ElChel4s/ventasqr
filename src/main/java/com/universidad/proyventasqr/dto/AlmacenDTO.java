package com.universidad.proyventasqr.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlmacenDTO {
    private Long id;
    private String nombre;
    private String ubicacion;
    private Integer capacidad;
    private String estado;
}