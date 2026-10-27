package com.empresa.sistema.servicio;

import com.empresa.sistema.dto.ProductoDto;
import com.empresa.sistema.excepcion.RecursoNoEncontradoException;
import com.empresa.sistema.modelo.Categoria;
import com.empresa.sistema.modelo.Producto;
import com.empresa.sistema.repositorio.CategoriaRepositorio;
import com.empresa.sistema.repositorio.ProductoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServicio {

    private final ProductoRepositorio productoRepositorio;
    private final CategoriaRepositorio categoriaRepositorio;

    @Transactional(readOnly = true)
    public List<ProductoDto> listarProductos() {
        return productoRepositorio.findAll().stream().map(this::aDto).toList();
    }

    @Transactional(readOnly = true)
    public ProductoDto buscarProductoPorId(Long id) {
        return aDto(obtenerProducto(id));
    }

    @Transactional
    public ProductoDto crearProducto(ProductoDto dto) {
        Producto producto = new Producto();
        copiarDatos(dto, producto);
        return aDto(productoRepositorio.save(producto));
    }

    @Transactional
    public ProductoDto actualizarProducto(Long id, ProductoDto dto) {
        Producto producto = obtenerProducto(id);
        copiarDatos(dto, producto);
        return aDto(productoRepositorio.save(producto));
    }

    @Transactional
    public void eliminarProducto(Long id) {
        productoRepositorio.delete(obtenerProducto(id));
    }

    // ---------- auxiliares ----------
    private Producto obtenerProducto(Long id) {
        return productoRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto", id));
    }

    private void copiarDatos(ProductoDto dto, Producto producto) {
        Categoria categoria = categoriaRepositorio.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría", dto.getCategoriaId()));
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setCategoria(categoria);
    }

    private ProductoDto aDto(Producto p) {
        return ProductoDto.builder()
                .id(p.getId())
                .nombre(p.getNombre())
                .precio(p.getPrecio())
                .stock(p.getStock())
                .categoriaId(p.getCategoria().getId())
                .categoriaNombre(p.getCategoria().getNombre())
                .build();
    }
}
