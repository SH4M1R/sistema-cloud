package com.empresa.sistema.servicio;

import com.empresa.sistema.dto.EmpleadoDto;
import com.empresa.sistema.excepcion.RecursoNoEncontradoException;
import com.empresa.sistema.modelo.Empleado;
import com.empresa.sistema.modelo.Rol;
import com.empresa.sistema.repositorio.EmpleadoRepositorio;
import com.empresa.sistema.repositorio.RolRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadoServicio {

    private final EmpleadoRepositorio empleadoRepositorio;
    private final RolRepositorio rolRepositorio;

    @Transactional(readOnly = true)
    public List<EmpleadoDto> listarEmpleados() {
        return empleadoRepositorio.findAll().stream().map(this::aDto).toList();
    }

    @Transactional(readOnly = true)
    public EmpleadoDto buscarEmpleadoPorId(Long id) {
        return aDto(obtenerEmpleado(id));
    }

    @Transactional
    public EmpleadoDto crearEmpleado(EmpleadoDto dto) {
        Empleado empleado = new Empleado();
        copiarDatos(dto, empleado);
        return aDto(empleadoRepositorio.save(empleado));
    }

    @Transactional
    public EmpleadoDto actualizarEmpleado(Long id, EmpleadoDto dto) {
        Empleado empleado = obtenerEmpleado(id);
        copiarDatos(dto, empleado);
        return aDto(empleadoRepositorio.save(empleado));
    }

    @Transactional
    public void eliminarEmpleado(Long id) {
        empleadoRepositorio.delete(obtenerEmpleado(id));
    }

    // ---------- auxiliares ----------
    private Empleado obtenerEmpleado(Long id) {
        return empleadoRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Empleado", id));
    }

    private void copiarDatos(EmpleadoDto dto, Empleado empleado) {
        Rol rol = rolRepositorio.findById(dto.getRolId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Rol", dto.getRolId()));
        empleado.setNombres(dto.getNombres());
        empleado.setApellidos(dto.getApellidos());
        empleado.setTelefono(dto.getTelefono());
        empleado.setRol(rol);
    }

    private EmpleadoDto aDto(Empleado e) {
        return EmpleadoDto.builder()
                .id(e.getId())
                .nombres(e.getNombres())
                .apellidos(e.getApellidos())
                .telefono(e.getTelefono())
                .rolId(e.getRol().getId())
                .rolNombre(e.getRol().getNombre())
                .build();
    }
}
