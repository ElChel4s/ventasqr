package com.universidad.proyventasqr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlmacenDTO {
    private Long idalmacen;
    private String nombre;
    private String ubicacion;
    private Integer capacidad;
    private Boolean estado;
}
