package com.universidad.proyventasqr.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidad.proyventasqr.dto.ProductoDTO;
import com.universidad.proyventasqr.model.Producto;
import com.universidad.proyventasqr.repository.ProductoRepository;
import com.universidad.proyventasqr.service.IProductoService;

@Service
public class ProductoServiceImpl implements IProductoService{
    @Autowired
    private ProductoRepository repo;
    @Autowired
    private CategoriaServiceImpl cate;
    @Override
    public List<ProductoDTO> obtenerTodosLosProductos() {
        return repo.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    private ProductoDTO convertToDTO(Producto producto){
        return ProductoDTO.builder()
        .idProd(producto.getIdProd())
        .nombre(producto.getNombre())
        .precio(producto.getPrecio())
        .stock(producto.getStock())
        .estado(producto.getEstado())
        .codigoQr(producto.getCodigoQr())
        .categoria(cate.convertToDTO(producto.getCategoria())).build();
    }
    
}
