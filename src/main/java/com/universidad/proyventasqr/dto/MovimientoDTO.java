package com.universidad.proyventasqr.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimientoDTO implements Serializable{
    private Long idMov;
    private String tipoMov;
    private LocalDate fecha;
    private String usuarioMov;
    private String estado;
    private AlmacenDTO almacen;
    
}
