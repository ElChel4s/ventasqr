package com.universidad.proyventasqr.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventarioDTO {
    private Integer id;
    private Integer productoId;
    private Long almacenId; // Cambiado a Long para reflejar la relación
    private BigDecimal cantidad;
    private LocalDateTime actualizadoEn;
}
