package com.universidad.proyventasqr.service.impl;

import com.universidad.proyventasqr.dto.InventarioDTO;
import com.universidad.proyventasqr.model.Almacen;
import com.universidad.proyventasqr.model.Inventario;
import com.universidad.proyventasqr.repository.InventarioRepository;
import com.universidad.proyventasqr.service.IInventarioService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventarioServiceImplTest {
    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private AlmacenRepository almacenRepository;

    @InjectMocks
    private InventarioServiceImpl inventarioService;

    public InventarioServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTodosLosInventarios() {
        Almacen almacen = Almacen.builder().idAlm(1L).nombre("Central").build();
        Inventario inv1 = Inventario.builder().id(1).productoId(1).almacen(almacen).cantidad(new java.math.BigDecimal("10")).actualizadoEn(java.time.LocalDateTime.now()).build();
        Inventario inv2 = Inventario.builder().id(2).productoId(2).almacen(almacen).cantidad(new java.math.BigDecimal("20")).actualizadoEn(java.time.LocalDateTime.now()).build();
        when(inventarioRepository.findAll()).thenReturn(Arrays.asList(inv1, inv2));
        List<InventarioDTO> resultado = inventarioService.obtenerTodosLosInventarios();
        assertEquals(2, resultado.size());
        assertEquals(1, resultado.get(0).getProductoId());
    }

    @Test
    void testEliminarInventario() {
        doNothing().when(inventarioRepository).deleteById(1);
        inventarioService.eliminarInventario(1);
        verify(inventarioRepository, times(1)).deleteById(1);
    }
}
