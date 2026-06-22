package com.example.Reservas.services;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.Reservas.dtos.AmbienteMasUsadoDto;
import com.example.Reservas.dtos.ReporteOcupacionDto;
import com.example.Reservas.entities.Reserva;
import com.example.Reservas.enums.EstadoReserva;
import com.example.Reservas.exception.ExcepcionRecursoNoEncontrado;
import com.example.Reservas.repositories.ReservaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReporteService {

    private final ReservaRepository reservaRepository;

    private static final double HORAS_INSTITUCIONALES = 16.0;

    public List<ReporteOcupacionDto> generarReporteOcupacion(LocalDate fecha) {

        LocalDateTime desde = fecha.atStartOfDay();

        LocalDateTime hasta = fecha.plusDays(1).atStartOfDay();

        List<Reserva> reservasDelDia = reservaRepository.findReservasActivasEntreFechas(
            EstadoReserva.Activa, desde, hasta
        );

        Map<String, Double> horasPorAmbiente = reservasDelDia.stream()
            .collect(Collectors.groupingBy(
            r -> r.getAmbiente().getNombre(),
            Collectors.summingDouble(this::calcularHoras)
        ));

        return horasPorAmbiente.entrySet().stream()
        .map(entry -> {
            double horas = entry.getValue();
            double porcentaje = (horas / HORAS_INSTITUCIONALES) * 100;
            return new ReporteOcupacionDto(entry.getKey(), horas, porcentaje);
        })
        .sorted(Comparator.comparing(ReporteOcupacionDto::getAmbiente))
        .toList();
    }

    public AmbienteMasUsadoDto generarReporteAmbienteMasUsado() {

        LocalDateTime desde = LocalDateTime.now().minusDays(7);
        LocalDateTime hasta = LocalDateTime.now();

        List<Reserva> reservasUltimaSemana = reservaRepository.findReservasActivasEntreFechas(
            EstadoReserva.Activa, desde, hasta
        );

        Map<String, List<Reserva>> reservasPorAmbiente = reservasUltimaSemana.stream()
            .collect(Collectors.groupingBy(r -> r.getAmbiente().getNombre()));

        return reservasPorAmbiente.entrySet().stream()
            .map(entry -> {
            String nombreAmbiente = entry.getKey();
            List<Reserva> reservas = entry.getValue();
            long cantidad = reservas.size();
            double totalHoras = reservas.stream()
                .mapToDouble(this::calcularHoras)
                .sum();
        return new AmbienteMasUsadoDto(nombreAmbiente, cantidad, totalHoras);
        })

        .max(Comparator.comparingLong(AmbienteMasUsadoDto::getCantidadReservas))
        .orElseThrow(() -> new ExcepcionRecursoNoEncontrado("No hay reservas registradas en los últimos 7 días."));
        }            

        private double calcularHoras(Reserva reserva) {
        return Duration.between(reserva.getFechaHoraInicio(), reserva.getFechaHoraFin()).toMinutes() / 60.0;
    }
}

