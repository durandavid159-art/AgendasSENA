package com.example.Reservas.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InstructorDto {

    private Long id;
    @NotBlank(message = "El nombre del instructor es obligatorio")
    private String nombre;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;
}
