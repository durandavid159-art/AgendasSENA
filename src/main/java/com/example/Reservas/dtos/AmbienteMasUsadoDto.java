package com.example.Reservas.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmbienteMasUsadoDto {
private String ambiente;
    private long cantidadReservas;
    private double totalHorasReservadas;
}
