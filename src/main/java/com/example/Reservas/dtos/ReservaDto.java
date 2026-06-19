package com.example.Reservas.dtos;

import java.time.LocalDateTime;

import com.example.Reservas.enums.EstadoReserva;

import lombok.Data;

@Data
public class ReservaDto {
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private Integer aprendices;
    private EstadoReserva estado;
}
