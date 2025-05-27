package com.univerwsidad.proyventasqr.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.univerwsidad.proyventasqr.dto.CategoriaDTO;
import com.univerwsidad.proyventasqr.model.Categoria;
import com.univerwsidad.proyventasqr.repository.CategoriaRepository;
import com.univerwsidad.proyventasqr.service.ICategoriaService;

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
    public CategoriaDTO actulizarCategoria(Long id, CategoriaDTO categoriaDTO) {
        Categoria categoriaExiste = categoriaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        categoriaExiste.setNombre(categoriaDTO.getNombre());
        categoriaExiste.setDescripcion(categoriaDTO.getDescripcion());

        Categoria categoriaActulizado = categoriaRepository.save(categoriaExiste);
        return convertToDTO(categoriaActulizado);
    }

    @Override
    public void eliminarCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }
    // Método auxiliar para convertir entidad a DTO
    private CategoriaDTO convertToDTO(Categoria categoria) {
        return CategoriaDTO.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .descripcion(categoria.getDescripcion())
                .build();
    }
    // Método auxiliar para convertir DTO a entidad
    private Categoria convertToEntity(CategoriaDTO categoriaDTO) {
        return Categoria.builder()
                .id(categoriaDTO.getId())
                .nombre(categoriaDTO.getNombre())
                .descripcion(categoriaDTO.getDescripcion())
                .build();
    }
     
}
