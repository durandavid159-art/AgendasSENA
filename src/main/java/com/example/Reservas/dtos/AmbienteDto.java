package com.example.Reservas.dtos;

import com.example.Reservas.enums.TipoAmbiente;

import lombok.Data;

@Data 

public class AmbienteDto {
    
    private Long id;
    private String nombre;
    private TipoAmbiente tipo;
    private Integer capacidad;
    private boolean activo;
}
