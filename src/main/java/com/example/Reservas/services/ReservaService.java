package com.example.Reservas.services;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Reservas.dtos.ReservaDto;
import com.example.Reservas.entities.Ambiente;
import com.example.Reservas.entities.Instructor;
import com.example.Reservas.entities.Reserva;
import com.example.Reservas.enums.EstadoReserva;
import com.example.Reservas.exception.ExcepcionNegocio;
import com.example.Reservas.exception.ExcepcionRecursoNoEncontrado;
import com.example.Reservas.repositories.ReservaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservaService {
    private final ReservaRepository reservaRepository;
    private final AmbienteService ambienteService;
    private final InstructorService instructorService;

    private static final LocalTime HORA_APERTURA = LocalTime.of(6, 0);
    private static final LocalTime HORA_CIERRE = LocalTime.of(22, 0);
    private static final int DURACION_MINIMA_HORAS = 1;
    private static final int DURACION_MAXIMA_HORAS = 4;
    private static final int MAX_RESERVAS_ACTIVAS_POR_DIA = 3;
    private static final int HORAS_MINIMAS_CANCELACION = 2;

    public ReservaDto crear(ReservaDto dto) {

        Ambiente ambiente = ambienteService.obtenerPorId(dto.getAmbienteId());
        Instructor instructor = instructorService.obtenerPorId(dto.getInstructorId());

        LocalDateTime inicio = LocalDateTime.of(dto.getFecha(), dto.getHoraInicio());
        LocalDateTime fin = LocalDateTime.of(dto.getFecha(), dto.getHoraFin());

        // Regla 8
        if (!inicio.isAfter(LocalDateTime.now())) {
            throw new ExcepcionNegocio("La fecha de inicio debe ser posterior al momento actual.");
        }

        if (!fin.isAfter(inicio)) {
            throw new ExcepcionNegocio("La fecha de fin debe ser posterior a la fecha de inicio.");
        }

        // Regla 4
        long minutos = Duration.between(inicio, fin).toMinutes();
        double horas = minutos / 60.0;
        if (horas < DURACION_MINIMA_HORAS || horas > DURACION_MAXIMA_HORAS) {
            throw new ExcepcionNegocio(
                "La reserva debe durar entre " + DURACION_MINIMA_HORAS + " y " + DURACION_MAXIMA_HORAS + " horas."
            );
        }

        // Regla 3 
        LocalTime horaInicio = inicio.toLocalTime();
        LocalTime horaFin = fin.toLocalTime();
        if (horaInicio.isBefore(HORA_APERTURA) || horaFin.isAfter(HORA_CIERRE)) {
            throw new ExcepcionNegocio("Las reservas solo pueden realizarse entre las 06:00 y las 22:00.");
        }

        // Regla 5
        if (!Boolean.TRUE.equals(ambiente.isActivo())) {
            throw new ExcepcionNegocio("El ambiente seleccionado no está activo.");
        }

        // Regla 2 
        if (dto.getNumeroAprendices() > ambiente.getCapacidad()) {
            throw new ExcepcionNegocio(
                "El número de aprendices (" + dto.getNumeroAprendices()+
                ") supera la capacidad del ambiente (" + ambiente.getCapacidad() + ")."
            );
        }

        // Regla 1 
        boolean haySolapamiento = reservaRepository.existeSolapamiento(
                ambiente.getId(), EstadoReserva.Activa , inicio, fin
        );
        if (haySolapamiento) {
            throw new ExcepcionNegocio("El ambiente ya tiene una reserva activa en ese horario.");
        }

        // Regla 6 
        long reservasDelDia = reservaRepository.contarReservasActivasInstructorEnFecha(
                instructor.getId(), EstadoReserva.Activa, dto.getFecha()
        );
        if (reservasDelDia >= MAX_RESERVAS_ACTIVAS_POR_DIA) {
            throw new ExcepcionNegocio(
                "El instructor ya tiene " + MAX_RESERVAS_ACTIVAS_POR_DIA + " reservas activas ese día."
            );
        }

        // Todo validado 
        Reserva reserva = Reserva.builder()
                .ambiente(ambiente)
                .instructor(instructor)
                .fechaHoraInicio(inicio)
                .fechaHoraFin(fin)
                .numeroAprendices(dto.getNumeroAprendices())
                .estado(EstadoReserva.Activa)
                .build();

        Reserva guardada = reservaRepository.save(reserva);
        return convertirADto(guardada);
    }

    public ReservaDto cancelar(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ExcepcionRecursoNoEncontrado("Reserva no encontrada con id: " + id));

        if (reserva.getEstado() != EstadoReserva.Activa) {
            throw new ExcepcionNegocio("Solo se pueden cancelar reservas que estén ACTIVAS.");
        }

        if (LocalDateTime.now().isAfter(reserva.getFechaHoraFin())) {
        throw new ExcepcionNegocio("No puedes modificar o cancelar una reserva que ya ha finalizado.");
        }

        // Regla 7 
        long horasParaInicio = Duration.between(LocalDateTime.now(), reserva.getFechaHoraInicio()).toHours();
        if (horasParaInicio < HORAS_MINIMAS_CANCELACION) {
            throw new ExcepcionNegocio(
                "Solo se puede cancelar una reserva si faltan al menos " + HORAS_MINIMAS_CANCELACION + " horas para su inicio."
            );
        }

        reserva.setEstado(EstadoReserva.Cancelada);
        Reserva actualizada = reservaRepository.save(reserva);
        return convertirADto(actualizada);
    }

    public List<ReservaDto> listarPorAmbienteYFecha(Long ambienteId, LocalDate fecha) {

        return reservaRepository.findReservasActivasPorAmbienteYFecha(ambienteId, fecha)
                .stream()
                .map(this::convertirADto)
                .toList();
    }

    private ReservaDto convertirADto(Reserva reserva) {

        ReservaDto dto = new ReservaDto();
        
        dto.setId(reserva.getId());
        dto.setAmbienteId(reserva.getAmbiente().getId());
        dto.setInstructorId(reserva.getInstructor().getId());
        dto.setFecha(reserva.getFechaHoraInicio().toLocalDate());
        dto.setHoraInicio(reserva.getFechaHoraInicio().toLocalTime());

        if (reserva.getFechaHoraFin() != null) {
        dto.setHoraFin(reserva.getFechaHoraFin().toLocalTime());
        }
        dto.setNumeroAprendices(reserva.getNumeroAprendices());
        
        if (reserva.getEstado() == EstadoReserva.Activa && LocalDateTime.now().isAfter(reserva.getFechaHoraFin())) {
        dto.setEstado(EstadoReserva.Finalizada);
        }else{

            dto.setEstado(reserva.getEstado());
        }

        return dto;
    }
}
