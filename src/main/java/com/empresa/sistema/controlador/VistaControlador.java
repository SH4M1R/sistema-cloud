package com.empresa.sistema.controlador;

import com.empresa.sistema.servicio.CategoriaServicio;
import com.empresa.sistema.servicio.EmpleadoServicio;
import com.empresa.sistema.servicio.ProductoServicio;
import com.empresa.sistema.servicio.RolServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class VistaControlador {

    private final ProductoServicio productoServicio;
    private final CategoriaServicio categoriaServicio;
    private final EmpleadoServicio empleadoServicio;
    private final RolServicio rolServicio;

    @GetMapping("/")
    public String raiz() {
        return "redirect:/inicio";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/inicio")
    public String inicio() {
        return "inicio";
    }

    @GetMapping("/productos")
    public String productos(Model modelo) {
        modelo.addAttribute("productos", productoServicio.listarProductos());
        modelo.addAttribute("categorias", categoriaServicio.listarCategorias());
        return "productos";
    }

    @GetMapping("/empleados")
    public String empleados(Model modelo) {
        modelo.addAttribute("empleados", empleadoServicio.listarEmpleados());
        modelo.addAttribute("roles", rolServicio.listarRoles());
        return "empleados";
    }
}
