package com.example.Reservas.dtos;

import java.time.LocalDateTime;

import lombok.Data;

@Data

public class ReservaDto {
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Integer aprendices;
    private String estado;
}
