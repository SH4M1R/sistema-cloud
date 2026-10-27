package com.empresa.sistema.controlador;

import com.empresa.sistema.dto.CategoriaDto;
import com.empresa.sistema.servicio.CategoriaServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaControlador {

    private final CategoriaServicio categoriaServicio;

    @GetMapping
    public List<CategoriaDto> listarCategorias() {
        return categoriaServicio.listarCategorias();
    }

    @PostMapping
    public ResponseEntity<CategoriaDto> crearCategoria(@Valid @RequestBody CategoriaDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaServicio.crearCategoria(dto));
    }
}
