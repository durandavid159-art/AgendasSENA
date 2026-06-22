package com.example.Reservas.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.Reservas.enums.EstadoReserva;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ReservaDto {
    private Long id;
    
    @NotNull(message = "El ambiente es obligatorio")
    private Long ambienteId;

    @NotNull(message = "El instructor es obligatorio")
    private Long instructorId;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin es obligatoria")
    private LocalTime horaFin;

    @NotNull(message = "El número de aprendices es obligatorio")
    @Positive(message = "El número de aprendices debe ser mayor a cero")
    private Integer numeroAprendices;

    private EstadoReserva estado;
    
}
