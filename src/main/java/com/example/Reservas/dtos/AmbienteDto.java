package com.example.Reservas.dtos;

import lombok.Data;

@Data 

public class AmbienteDto {
    private String tipo;
    private Integer capacidad;
    private boolean activo;

}
