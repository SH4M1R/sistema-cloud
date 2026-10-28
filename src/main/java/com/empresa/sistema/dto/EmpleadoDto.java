package com.empresa.sistema.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class EmpleadoDto {

    private Long id;

    @NotBlank(message = "Los nombres son obligatorios")
    @Size(max = 100)
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 100)
    private String apellidos;
    
    @Size(max = 15)
    private String telefono;

    @NotNull(message = "El rol es obligatorio")
    private Long rolId;

    // Solo de salida: se llena al responder
    private String rolNombre;
}
