package com.example.Reservas.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteOcupacion {
    private String ambiente;
    private double horasReservadas;
    private double porcentajeOcupacion;
}
