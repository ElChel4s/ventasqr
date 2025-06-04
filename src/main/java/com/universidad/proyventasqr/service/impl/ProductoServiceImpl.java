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
                .orElseThrow(() -> new RuntimeException(
                        "La Categoría con ID: " + productoDTO.getCategoria().getId() + " no existe"));
        Producto producto = modelMapper.map(productoDTO, Producto.class);
        producto.setCategoria(categoria);
        Producto guardaProducto = productoRepository.save(producto);
        return modelMapper.map(guardaProducto, ProductoDTO.class);
    }

    @Override
    public ProductoDTO obtenerProductoPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El Producto con ID: " + id + " no existe"));
        return modelMapper.map(producto, ProductoDTO.class);
    }

    @Override
    @Transactional
    public List<ProductoDTO> obtenerProductosPorCategoria(Long catId) {
        return productoRepository.findByCategoriaId(catId).stream()
                .map(producto -> modelMapper.map(producto, ProductoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductoDTO actualizarProducto(Long id, ProductoDTO productoDTO) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El Producto con ID: " + id + " no existe"));

        // Verificar si se está cambiando la categoría
        if (!producto.getCategoria().getId().equals(productoDTO.getCategoria().getId())) {
            Categoria nuevaCategoria = categoriaRepository.findById(productoDTO.getCategoria().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "la Categoría con id: " + productoDTO.getCategoria().getId() + " no existe"));
            producto.setCategoria(nuevaCategoria);
        }

        producto.setNombre(productoDTO.getNombre());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setStock(productoDTO.getStock());
        producto.setEstado(productoDTO.getEstado());
        producto.setCodigoQr(productoDTO.getCodigoQr());

        Producto updatedProducto = productoRepository.save(producto);
        return modelMapper.map(updatedProducto, ProductoDTO.class);
    }

    // @Override
    // @Transactional
    // public void eliminarProducto(Long id) {
    //     Producto producto = productoRepository.findById(id)
    //             .orElseThrow(() -> new RuntimeException("El producto con ID: " + id + " no existe"));
    //     productoRepository.delete(producto);
    // }
    @Override
    @Transactional
    public void eliminarProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El producto con ID: " + id + " no existe"));
        producto.setEstado("inactivo");
    }

}
