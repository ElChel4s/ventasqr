package com.universidad.proyventasqr.service.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidad.proyventasqr.dto.CategoriaDTO;
import com.universidad.proyventasqr.model.Categoria;
import com.universidad.proyventasqr.repository.CategoriaRepository;
import com.universidad.proyventasqr.service.ICategoriaService;

@Service
public class CategoriaServiceImpl implements ICategoriaService {
    
    @Autowired
    private CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public List<CategoriaDTO> obtenerTodasLasCategorias() {
        return categoriaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoriaDTO crearCategoria(CategoriaDTO categoriaDTO) {
        Categoria categoria = convertToEntity(categoriaDTO);
        Categoria categoriaGuardado = categoriaRepository.save(categoria);
        return convertToDTO(categoriaGuardado);
    }

    @Override
    public CategoriaDTO actualizarCategoria(Long id, CategoriaDTO categoriaDTO) {
        Categoria categoriaExiste = categoriaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        categoriaExiste.setNombre(categoriaDTO.getNombre());
        categoriaExiste.setDescripcion(categoriaDTO.getDescripcion());

        Categoria categoriaActulizado = categoriaRepository.save(categoriaExiste);
        return convertToDTO(categoriaActulizado);
    }

    // Método para eliminar (de manera lógica) una categoria por su ID
    @Override
    public CategoriaDTO eliminarCategoria(Long id, CategoriaDTO categoriaDTO) {
        Categoria categoriaExiste = categoriaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        categoriaExiste.setEstado("INACTIVO");
        categoriaExiste.setFecha_baja(LocalDate.now());
        categoriaExiste.setMotivoBaja(categoriaDTO.getMotivoBaja());

        Categoria categoriaInactivo = categoriaRepository.save(categoriaExiste);
        return convertToDTO(categoriaInactivo);
    }
    /* metodo para eliminar el registro permanentemente
    @Override
    public void eliminarCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }*/

    @Override
    public CategoriaDTO obtenerCategoriaPorNombre(String nombreCategoria) {
        Categoria categoria = categoriaRepository.findByNombre(nombreCategoria);
        return convertToDTO(categoria);
    }

    @Override
    public List<CategoriaDTO> obtenerCategoriaAsc() {
        return categoriaRepository.findAllByOrderByNombreAsc().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    // Método auxiliar para convertir entidad a DTO
    public CategoriaDTO convertToDTO(Categoria categoria) {
        return CategoriaDTO.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .descripcion(categoria.getDescripcion())
                .fechaBaja(categoria.getFecha_baja())
                .fechaAlta(categoria.getFecha_alta())
                .motivoBaja(categoria.getMotivoBaja())
                .build();
    }
    // Método auxiliar para convertir DTO a entidad
    private Categoria convertToEntity(CategoriaDTO categoriaDTO) {
        return Categoria.builder()
                .id(categoriaDTO.getId())
                .nombre(categoriaDTO.getNombre())
                .descripcion(categoriaDTO.getDescripcion())
                .fecha_baja(categoriaDTO.getFechaBaja())
                .fecha_alta(categoriaDTO.getFechaAlta())
                .motivoBaja(categoriaDTO.getMotivoBaja())
                .build();
    }
     
}