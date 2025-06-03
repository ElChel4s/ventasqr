package com.universidad.proyventasqr.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidad.proyventasqr.dto.ProductoDTO;
import com.universidad.proyventasqr.model.Categoria;
import com.universidad.proyventasqr.model.Producto;
import com.universidad.proyventasqr.repository.CategoriaRepository;
import com.universidad.proyventasqr.repository.ProductoRepository;
import com.universidad.proyventasqr.service.IProductoService;

import jakarta.transaction.Transactional;

@Service
public class ProductoServiceImpl implements IProductoService {
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ProductoDTO> obtenerTodosLosProductos() {
        return productoRepository.findAll().stream().map(producto -> modelMapper.map(producto, ProductoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductoDTO crearProducto(ProductoDTO productoDTO) {
        if (productoDTO.getCategoria() == null || productoDTO.getCategoria().getId() == null) {
            throw new IllegalArgumentException("La categoría es obligatoria");
        }
        Categoria categoria = categoriaRepository.findById(productoDTO.getCategoria().getId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        Producto producto = modelMapper.map(productoDTO, Producto.class);
        producto.setCategoria(categoria);
        Producto guardaProducto = productoRepository.save(producto);
        return modelMapper.map(guardaProducto, ProductoDTO.class);
    }

    @Override
    public ProductoDTO obtenerProductoPorId(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerProductoPorId'");
    }

    @Override
    public List<ProductoDTO> obtenerProductosPorCategoria(Long catId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerProductosPorCategoria'");
    }

    @Override
    public ProductoDTO actualizarProducto(Long id, ProductoDTO productoDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizarProducto'");
    }

    @Override
    public void eliminarProducto(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminarProducto'");
    }

}
