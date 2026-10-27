package com.empresa.sistema.servicio;

import com.empresa.sistema.dto.RolDto;
import com.empresa.sistema.modelo.Rol;
import com.empresa.sistema.repositorio.RolRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolServicio {

    private final RolRepositorio rolRepositorio;

    @Transactional(readOnly = true)
    public List<RolDto> listarRoles() {
        return rolRepositorio.findAll().stream().map(this::aDto).toList();
    }

    @Transactional
    public RolDto crearRol(RolDto dto) {
        Rol rol = Rol.builder().nombre(dto.getNombre()).build();
        return aDto(rolRepositorio.save(rol));
    }

    private RolDto aDto(Rol rol) {
        return RolDto.builder().id(rol.getId()).nombre(rol.getNombre()).build();
    }
}
