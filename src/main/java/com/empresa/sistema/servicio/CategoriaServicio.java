package com.empresa.sistema.servicio;

import com.empresa.sistema.dto.CategoriaDto;
import com.empresa.sistema.modelo.Categoria;
import com.empresa.sistema.repositorio.CategoriaRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServicio {

    private final CategoriaRepositorio categoriaRepositorio;

    @Transactional(readOnly = true)
    public List<CategoriaDto> listarCategorias() {
        return categoriaRepositorio.findAll().stream().map(this::aDto).toList();
    }

    @Transactional
    public CategoriaDto crearCategoria(CategoriaDto dto) {
        Categoria categoria = Categoria.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .build();
        return aDto(categoriaRepositorio.save(categoria));
    }

    private CategoriaDto aDto(Categoria c) {
        return CategoriaDto.builder()
                .id(c.getId())
                .nombre(c.getNombre())
                .descripcion(c.getDescripcion())
                .build();
    }
}
