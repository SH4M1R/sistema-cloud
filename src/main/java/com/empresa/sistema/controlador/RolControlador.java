package com.empresa.sistema.controlador;

import com.empresa.sistema.dto.RolDto;
import com.empresa.sistema.servicio.RolServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolControlador {

    private final RolServicio rolServicio;

    @GetMapping
    public List<RolDto> listarRoles() {
        return rolServicio.listarRoles();
    }

    @PostMapping
    public ResponseEntity<RolDto> crearRol(@Valid @RequestBody RolDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rolServicio.crearRol(dto));
    }
}
