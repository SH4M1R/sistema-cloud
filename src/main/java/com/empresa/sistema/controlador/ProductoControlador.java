package com.empresa.sistema.controlador;

import com.empresa.sistema.dto.ProductoDto;
import com.empresa.sistema.servicio.ProductoServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoControlador {

    private final ProductoServicio productoServicio;

    @GetMapping
    public List<ProductoDto> listarProductos() {
        return productoServicio.listarProductos();
    }

    @GetMapping("/{id}")
    public ProductoDto buscarProductoPorId(@PathVariable Long id) {
        return productoServicio.buscarProductoPorId(id);
    }

    @PostMapping
    public ResponseEntity<ProductoDto> crearProducto(@Valid @RequestBody ProductoDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoServicio.crearProducto(dto));
    }

    @PutMapping("/{id}")
    public ProductoDto actualizarProducto(@PathVariable Long id, @Valid @RequestBody ProductoDto dto) {
        return productoServicio.actualizarProducto(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoServicio.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
