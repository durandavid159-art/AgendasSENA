package com.example.Reservas.dtos;

import com.example.Reservas.enums.TipoAmbiente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data 

public class AmbienteDto {
    
    private Long id;
    @NotBlank(message = "El nombre del ambiente es obligatorio")
    private String nombre;

    @NotNull(message = "El tipo de ambiente es obligatorio")
    private TipoAmbiente tipo;

    @NotNull(message = "La capacidad es obligatoria")
    @Positive(message = "La capacidad debe ser mayor a cero")
    private Integer capacidad;

    @NotNull(message = "El estado activo es obligatorio")
    private boolean activo;
}
