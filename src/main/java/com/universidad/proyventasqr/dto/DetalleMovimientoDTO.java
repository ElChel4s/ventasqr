package com.universidad.proyventasqr.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleMovimientoDTO implements Serializable{
    private Long id;
    private ProductoDTO producto;
    // private MovimientoDTO movimiento;
    private int cantidad;
}
