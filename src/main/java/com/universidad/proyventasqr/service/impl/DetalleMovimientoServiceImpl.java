package com.universidad.proyventasqr.service.impl;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidad.proyventasqr.dto.ProductoDTO;
import com.universidad.proyventasqr.model.DetalleMovimiento;
import com.universidad.proyventasqr.repository.DetalleMovimientoRepository;
import com.universidad.proyventasqr.service.IDetalleMovimientoService;

@Service
public class DetalleMovimientoServiceImpl implements IDetalleMovimientoService{
    @Autowired
    private DetalleMovimientoRepository detalleRepository;
    @Autowired
    private CategoriaServiceImpl cate;
    public List<ProductoDTO> obtenerProductosPorMovimiento(Long id){
        List<DetalleMovimiento> detalles = detalleRepository.findByMovimientoIdWithProductos(id);
        return detalles.stream().map(detalle->{
            ProductoDTO productoDTO = new ProductoDTO();
            productoDTO.setIdProd(detalle.getProducto().getIdProd());
            productoDTO.setNombre(detalle.getProducto().getNombre());
            productoDTO.setPrecio(detalle.getProducto().getPrecio());
            productoDTO.setStock(detalle.getProducto().getStock());
            productoDTO.setEstado(detalle.getProducto().getEstado());
            productoDTO.setCodigoQr(detalle.getProducto().getCodigoQr());
            productoDTO.setCategoria(cate.convertToDTO(detalle.getProducto().getCategoria()));
            return productoDTO;
        }).collect(Collectors.toList());
    }

}
