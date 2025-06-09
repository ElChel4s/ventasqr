package com.universidad.proyventasqr.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidad.proyventasqr.dto.MovimientoDTO;
import com.universidad.proyventasqr.model.Almacen;
import com.universidad.proyventasqr.model.DetalleMovimiento;
import com.universidad.proyventasqr.model.Movimiento;
import com.universidad.proyventasqr.model.Producto;
import com.universidad.proyventasqr.repository.AlmacenRepository;
import com.universidad.proyventasqr.repository.MovimientoRepository;
import com.universidad.proyventasqr.repository.ProductoRepository;
import com.universidad.proyventasqr.service.IMovimientoService;

import jakarta.transaction.Transactional;

@Service
public class MovimientoServiceImpl implements IMovimientoService{
    @Autowired
    private MovimientoRepository movimientoRepository;
    @Autowired
    private AlmacenRepository almacenRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private com.universidad.proyventasqr.repository.UsuarioRepository usuarioRepository;
    @Autowired
    private com.universidad.proyventasqr.repository.InventarioRepository inventarioRepository;

    @Override
    public List<MovimientoDTO> obtenerTodosLosMovimientos() {
        return  movimientoRepository.findAll().stream().map(
            movimiento -> modelMapper.map(movimiento, MovimientoDTO.class)
        ).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MovimientoDTO crearMovimiento(MovimientoDTO movimientoDTO) {
        Movimiento movimiento = modelMapper.map(movimientoDTO, Movimiento.class);
        // Asignar almacenes según el tipo de movimiento
        if ("ENTRADA".equalsIgnoreCase(movimientoDTO.getTipoMov())) {
            if (movimientoDTO.getAlmacenDestino() == null || movimientoDTO.getAlmacenDestino().getIdAlm() == null) {
                throw new IllegalArgumentException("Debe ingresar un almacén destino válido");
            }
            Almacen destino = almacenRepository.findById(movimientoDTO.getAlmacenDestino().getIdAlm())
                .orElseThrow(() -> new RuntimeException("El almacén destino no existe"));
            movimiento.setAlmacenDestino(destino);
        } else if ("SALIDA".equalsIgnoreCase(movimientoDTO.getTipoMov())) {
            if (movimientoDTO.getAlmacenOrigen() == null || movimientoDTO.getAlmacenOrigen().getIdAlm() == null) {
                throw new IllegalArgumentException("Debe ingresar un almacén origen válido");
            }
            Almacen origen = almacenRepository.findById(movimientoDTO.getAlmacenOrigen().getIdAlm())
                .orElseThrow(() -> new RuntimeException("El almacén origen no existe"));
            movimiento.setAlmacenOrigen(origen);
        } else if ("TRASPASO".equalsIgnoreCase(movimientoDTO.getTipoMov())) {
            if (movimientoDTO.getAlmacenOrigen() == null || movimientoDTO.getAlmacenDestino() == null
                || movimientoDTO.getAlmacenOrigen().getIdAlm() == null || movimientoDTO.getAlmacenDestino().getIdAlm() == null) {
                throw new IllegalArgumentException("Debe especificar almacén origen y destino para traspaso");
            }
            Almacen origen = almacenRepository.findById(movimientoDTO.getAlmacenOrigen().getIdAlm())
                .orElseThrow(() -> new RuntimeException("Almacén origen no encontrado"));
            Almacen destino = almacenRepository.findById(movimientoDTO.getAlmacenDestino().getIdAlm())
                .orElseThrow(() -> new RuntimeException("Almacén destino no encontrado"));
            movimiento.setAlmacenOrigen(origen);
            movimiento.setAlmacenDestino(destino);
        }
        // Procesar detalles si vienen en el DTO
        if (movimientoDTO.getDetalles() != null && !movimientoDTO.getDetalles().isEmpty()) {
            List<DetalleMovimiento> detalles = movimientoDTO.getDetalles().stream().map(detDTO -> {
                DetalleMovimiento det = new DetalleMovimiento();
                det.setMovimiento(movimiento);
                det.setCantidad(detDTO.getCantidad());
                if(detDTO.getProducto() == null || detDTO.getProducto().getIdProd() == null)
                    throw new IllegalArgumentException("Producto requerido en detalle");
                Producto producto = productoRepository.findById(detDTO.getProducto().getIdProd())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
                det.setProducto(producto);
                return det;
            }).collect(java.util.stream.Collectors.toList());
            movimiento.setProductos(detalles);
        }
        // Asignar fecha automática si no viene en el DTO
        if (movimiento.getFecha() == null) {
            movimiento.setFecha(java.time.LocalDate.now());
        }
        Movimiento guardarMovimiento = movimientoRepository.save(movimiento);
        return modelMapper.map(guardarMovimiento, MovimientoDTO.class);
        
    }

    @Override
    public MovimientoDTO obtenerMovimientoPorId(Long id) {
        Movimiento movimiento = movimientoRepository.findById(id).orElseThrow(()->new RuntimeException("El movimiento con ID: "+id+" no existe"));
        return modelMapper.map(movimiento, MovimientoDTO.class);
    }

    @Override
    @Transactional
    public MovimientoDTO actualizarMovimiento(Long id, MovimientoDTO movimientoDTO) {
        Movimiento movimiento = movimientoRepository.findById(id).orElseThrow(()->new RuntimeException("El movimiento con ID: "+id+" no existe"));
        // Eliminar lógica de actualización de almacen en actualizarMovimiento
        // if(!movimiento.getAlmacen().getId().equals(movimientoDTO.getAlmacen().getIdAlm())){
        //     Almacen nuevoAlmacen = almacenRepository.findById(movimientoDTO.getAlmacen().getIdAlm()).orElseThrow(()->new RuntimeException("El almacen con ID:"+movimientoDTO.getAlmacen().getIdAlm()+" no existe"));
        //     movimiento.setAlmacen(nuevoAlmacen);
        // }
        // Ahora solo actualizar origen/destino si aplica
        if ("ENTRADA".equalsIgnoreCase(movimientoDTO.getTipoMov())) {
            if (movimientoDTO.getAlmacenDestino() != null && movimientoDTO.getAlmacenDestino().getIdAlm() != null) {
                Almacen destino = almacenRepository.findById(movimientoDTO.getAlmacenDestino().getIdAlm())
                    .orElseThrow(() -> new RuntimeException("El almacén destino no existe"));
                movimiento.setAlmacenDestino(destino);
            }
        }
        if ("SALIDA".equalsIgnoreCase(movimientoDTO.getTipoMov())) {
            if (movimientoDTO.getAlmacenOrigen() != null && movimientoDTO.getAlmacenOrigen().getIdAlm() != null) {
                Almacen origen = almacenRepository.findById(movimientoDTO.getAlmacenOrigen().getIdAlm())
                    .orElseThrow(() -> new RuntimeException("El almacén origen no existe"));
                movimiento.setAlmacenOrigen(origen);
            }
        }
        if ("TRASPASO".equalsIgnoreCase(movimientoDTO.getTipoMov())) {
            if (movimientoDTO.getAlmacenOrigen() != null && movimientoDTO.getAlmacenOrigen().getIdAlm() != null) {
                Almacen origen = almacenRepository.findById(movimientoDTO.getAlmacenOrigen().getIdAlm())
                    .orElseThrow(() -> new RuntimeException("Almacén origen no encontrado"));
                movimiento.setAlmacenOrigen(origen);
            }
            if (movimientoDTO.getAlmacenDestino() != null && movimientoDTO.getAlmacenDestino().getIdAlm() != null) {
                Almacen destino = almacenRepository.findById(movimientoDTO.getAlmacenDestino().getIdAlm())
                    .orElseThrow(() -> new RuntimeException("Almacén destino no encontrado"));
                movimiento.setAlmacenDestino(destino);
            }
        }
        movimiento.setTipoMov(movimientoDTO.getTipoMov());
        movimiento.setFecha(movimientoDTO.getFecha());
        movimiento.setUsuarioMov(movimientoDTO.getUsuarioMov());
        movimiento.setEstado(movimientoDTO.getEstado());
        Movimiento updateMov = movimientoRepository.save(movimiento);
        return modelMapper.map(updateMov, MovimientoDTO.class);
    }

    @Override
    @Transactional
    public void eliminarMovimiento(Long id) {
        Movimiento movimiento = movimientoRepository.findById(id).orElseThrow(()->new RuntimeException("El movimiento con ID: "+id+" no existe"));
        movimientoRepository.delete(movimiento);
    }
    
    @Override
    @Transactional
    public MovimientoDTO crearMovimientoConUsuario(MovimientoDTO movimientoDTO, String username) {
        Movimiento movimiento = modelMapper.map(movimientoDTO, Movimiento.class);
        // Eliminar referencias a 'almacen' y usar solo almacenOrigen/almacenDestino
        // if (movimientoDTO.getAlmacen() == null || movimientoDTO.getAlmacen().getIdAlm() == null){
        //     throw new IllegalArgumentException("Debe ingresar un almacen valido");
        // }
        // Almacen almacen = almacenRepository.findById(movimientoDTO.getAlmacen().getIdAlm())
        //     .orElseThrow(() -> new RuntimeException("El almacen con ID: " + movimientoDTO.getAlmacen().getIdAlm() + " no existe"));
        // movimiento.setAlmacen(almacen);
        // Asignar usuario autenticado
        com.universidad.proyventasqr.model.Usuario usuario = usuarioRepository.findByNombreUsuario(username)
            .orElseThrow(() -> new RuntimeException("Usuario autenticado no encontrado"));
        movimiento.setUsuario(usuario);
        movimiento.setUsuarioMov(username); // Opcional: también en campo usuarioMov
        // Asignar almacenes origen/destino en la entidad para que se persistan en la BD
        if ("ENTRADA".equalsIgnoreCase(movimiento.getTipoMov())) {
            if (movimientoDTO.getAlmacenDestino() == null || movimientoDTO.getAlmacenDestino().getIdAlm() == null) {
                throw new IllegalArgumentException("Debe ingresar un almacén destino válido");
            }
            Almacen destino = almacenRepository.findById(movimientoDTO.getAlmacenDestino().getIdAlm())
                .orElseThrow(() -> new RuntimeException("El almacén destino no existe"));
            movimiento.setAlmacenDestino(destino);
        }
        if ("SALIDA".equalsIgnoreCase(movimiento.getTipoMov())) {
            if (movimientoDTO.getAlmacenOrigen() == null || movimientoDTO.getAlmacenOrigen().getIdAlm() == null) {
                throw new IllegalArgumentException("Debe ingresar un almacén origen válido");
            }
            Almacen origen = almacenRepository.findById(movimientoDTO.getAlmacenOrigen().getIdAlm())
                .orElseThrow(() -> new RuntimeException("El almacén origen no existe"));
            movimiento.setAlmacenOrigen(origen);
        }
        if ("TRASPASO".equalsIgnoreCase(movimiento.getTipoMov())) {
            if (movimientoDTO.getAlmacenOrigen() == null || movimientoDTO.getAlmacenDestino() == null
                || movimientoDTO.getAlmacenOrigen().getIdAlm() == null || movimientoDTO.getAlmacenDestino().getIdAlm() == null) {
                throw new IllegalArgumentException("Debe especificar almacén origen y destino para traspaso");
            }
            Almacen origen = almacenRepository.findById(movimientoDTO.getAlmacenOrigen().getIdAlm())
                .orElseThrow(() -> new RuntimeException("Almacén origen no encontrado"));
            Almacen destino = almacenRepository.findById(movimientoDTO.getAlmacenDestino().getIdAlm())
                .orElseThrow(() -> new RuntimeException("Almacén destino no encontrado"));
            movimiento.setAlmacenOrigen(origen);
            movimiento.setAlmacenDestino(destino);
        }
        // Procesar detalles si vienen en el DTO
        if (movimientoDTO.getDetalles() != null && !movimientoDTO.getDetalles().isEmpty()) {
            List<DetalleMovimiento> detalles = movimientoDTO.getDetalles().stream().map(detDTO -> {
                DetalleMovimiento det = new DetalleMovimiento();
                det.setMovimiento(movimiento);
                det.setCantidad(detDTO.getCantidad());
                if(detDTO.getProducto() == null || detDTO.getProducto().getIdProd() == null)
                    throw new IllegalArgumentException("Producto requerido en detalle");
                Producto producto = productoRepository.findById(detDTO.getProducto().getIdProd())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
                det.setProducto(producto);
                // --- ACTUALIZAR INVENTARIO SEGÚN TIPO DE MOVIMIENTO ---
                if ("ENTRADA".equalsIgnoreCase(movimientoDTO.getTipoMov())) {
                    Almacen destinoInventario = movimiento.getAlmacenDestino();
                    com.universidad.proyventasqr.model.Inventario inventario = inventarioRepository
                        .findByAlmacen_Id(destinoInventario.getId()).stream()
                        .filter(inv -> inv.getProducto().getIdProd().equals(producto.getIdProd()))
                        .findFirst()
                        .orElse(null);
                    if (inventario == null) {
                        inventario = new com.universidad.proyventasqr.model.Inventario();
                        inventario.setAlmacen(destinoInventario);
                        inventario.setProducto(producto);
                        inventario.setCantidad(det.getCantidad());
                        inventario.setActualizadoEn(java.time.LocalDateTime.now());
                    } else {
                        inventario.setCantidad(inventario.getCantidad().add(det.getCantidad()));
                        inventario.setActualizadoEn(java.time.LocalDateTime.now());
                    }
                    inventarioRepository.save(inventario);
                } else if ("SALIDA".equalsIgnoreCase(movimientoDTO.getTipoMov())) {
                    Almacen origenInventario = movimiento.getAlmacenOrigen();
                    com.universidad.proyventasqr.model.Inventario inventario = inventarioRepository
                        .findByAlmacen_Id(origenInventario.getId()).stream()
                        .filter(inv -> inv.getProducto().getIdProd().equals(producto.getIdProd()))
                        .findFirst()
                        .orElse(null);
                    if (inventario == null || inventario.getCantidad().compareTo(det.getCantidad()) < 0) {
                        throw new RuntimeException("Stock insuficiente para el producto " + producto.getNombre());
                    }
                    inventario.setCantidad(inventario.getCantidad().subtract(det.getCantidad()));
                    inventario.setActualizadoEn(java.time.LocalDateTime.now());
                    inventarioRepository.save(inventario);
                } else if ("TRASPASO".equalsIgnoreCase(movimientoDTO.getTipoMov())) {
                    Almacen origenInventario = movimiento.getAlmacenOrigen();
                    Almacen destinoInventario = movimiento.getAlmacenDestino();
                    // Restar del origen
                    com.universidad.proyventasqr.model.Inventario invOrigen = inventarioRepository
                        .findByAlmacen_Id(origenInventario.getId()).stream()
                        .filter(inv -> inv.getProducto().getIdProd().equals(producto.getIdProd()))
                        .findFirst()
                        .orElse(null);
                    if (invOrigen == null || invOrigen.getCantidad().compareTo(det.getCantidad()) < 0) {
                        throw new RuntimeException("Stock insuficiente en almacén origen para el producto " + producto.getNombre());
                    }
                    invOrigen.setCantidad(invOrigen.getCantidad().subtract(det.getCantidad()));
                    invOrigen.setActualizadoEn(java.time.LocalDateTime.now());
                    inventarioRepository.save(invOrigen);
                    // Sumar al destino
                    com.universidad.proyventasqr.model.Inventario invDestino = inventarioRepository
                        .findByAlmacen_Id(destinoInventario.getId()).stream()
                        .filter(inv -> inv.getProducto().getIdProd().equals(producto.getIdProd()))
                        .findFirst()
                        .orElse(null);
                    if (invDestino == null) {
                        invDestino = new com.universidad.proyventasqr.model.Inventario();
                        invDestino.setAlmacen(destinoInventario);
                        invDestino.setProducto(producto);
                        invDestino.setCantidad(det.getCantidad());
                        invDestino.setActualizadoEn(java.time.LocalDateTime.now());
                    } else {
                        invDestino.setCantidad(invDestino.getCantidad().add(det.getCantidad()));
                        invDestino.setActualizadoEn(java.time.LocalDateTime.now());
                    }
                    inventarioRepository.save(invDestino);
                }
                // --- FIN ACTUALIZAR INVENTARIO ---
                return det;
            }).collect(java.util.stream.Collectors.toList());
            movimiento.setProductos(detalles);
        }
        // Asignar fecha automática si no viene en el DTO
        if (movimiento.getFecha() == null) {
            movimiento.setFecha(java.time.LocalDate.now());
        }
        Movimiento guardarMovimiento = movimientoRepository.save(movimiento);
        MovimientoDTO result = modelMapper.map(guardarMovimiento, MovimientoDTO.class);
        // Asegurar que los IDs de almacenOrigen y almacenDestino estén correctamente mapeados
        if (result.getAlmacenOrigen() != null && guardarMovimiento.getAlmacenOrigen() != null) {
            result.getAlmacenOrigen().setIdAlm(guardarMovimiento.getAlmacenOrigen().getId());
        }
        if (result.getAlmacenDestino() != null && guardarMovimiento.getAlmacenDestino() != null) {
            result.getAlmacenDestino().setIdAlm(guardarMovimiento.getAlmacenDestino().getId());
        }
        return result;
    }
}
