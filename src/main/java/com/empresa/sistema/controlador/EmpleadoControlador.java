package com.empresa.sistema.controlador;

import com.empresa.sistema.dto.EmpleadoDto;
import com.empresa.sistema.servicio.EmpleadoServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@RequiredArgsConstructor
public class EmpleadoControlador {

    private final EmpleadoServicio empleadoServicio;

    @GetMapping
    public List<EmpleadoDto> listarEmpleados() {
        return empleadoServicio.listarEmpleados();
    }

    @GetMapping("/{id}")
    public EmpleadoDto buscarEmpleadoPorId(@PathVariable Long id) {
        return empleadoServicio.buscarEmpleadoPorId(id);
    }

    @PostMapping
    public ResponseEntity<EmpleadoDto> crearEmpleado(@Valid @RequestBody EmpleadoDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoServicio.crearEmpleado(dto));
    }

    @PutMapping("/{id}")
    public EmpleadoDto actualizarEmpleado(@PathVariable Long id, @Valid @RequestBody EmpleadoDto dto) {
        return empleadoServicio.actualizarEmpleado(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Long id) {
        empleadoServicio.eliminarEmpleado(id);
        return ResponseEntity.noContent().build();
    }
}
