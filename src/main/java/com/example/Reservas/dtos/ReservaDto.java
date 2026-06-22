package com.example.Reservas.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.Reservas.enums.EstadoReserva;
import lombok.Data;

@Data
public class ReservaDto {
    private Long id;
    private Long ambienteId;
    private Long instructorId;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Integer numeroAprendices;
    private EstadoReserva estado;
    
}
