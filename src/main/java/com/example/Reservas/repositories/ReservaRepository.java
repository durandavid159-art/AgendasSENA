package com.example.Reservas.repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Reservas.entities.Ambiente;
import com.example.Reservas.entities.Reserva;
import com.example.Reservas.enums.EstadoReserva;


public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    // Regla 1 
    @Query("""
        SELECT COUNT(r) > 0
        FROM Reserva r
        WHERE r.ambiente.id = :ambienteId
            AND r.estado = :estado
            AND r.fechaHoraInicio < :fin
            AND r.fechaHoraFin > :inicio
    """)

    boolean existeSolapamiento(
        @Param("ambienteId") Long ambienteId, 
        @Param("estado")EstadoReserva estado,
        @Param("inicio") LocalDateTime inicio,
        @Param("fin") LocalDateTime fin
    );

    // Regla 6 
    @Query("""
        SELECT COUNT(r) 
        FROM Reserva r
        WHERE r.instructor.id = :instructorId
            AND r.estado = :estado
            AND CAST (r.fechaHoraInicio AS localdate) = CAST(:fecha AS localdate)
    """)
    long contarReservasActivasInstructorEnFecha(
        @Param("instructorId") Long instructorId,
        @Param("estado")EstadoReserva estado,
        @Param("fecha") LocalDate fecha
    );

    // Reservas ambiente
    @Query("""
        SELECT r 
        FROM Reserva r
        WHERE r.ambiente.id = :ambienteId
            AND CAST(r.fechaHoraInicio AS localdate) = CAST(:fecha AS localdate)
    """)
    List<Reserva> findReservasActivasPorAmbienteYFecha(
        @Param("ambienteId") Long ambienteId,
        @Param("fecha") LocalDate fecha
    );

    // Reporte de ocupación 
    @Query("""
        SELECT r 
        FROM Reserva r
        WHERE r.ambiente.id = :ambienteId 
            AND CAST(r.fechaHoraInicio AS localdate) = CAST(:fecha AS localdate)
    """)
    List<Reserva> findReservasActivasPorFecha(
        @Param ("ambienteId") Long anbienteId,
        @Param("fecha") LocalDateTime fecha        
    );

    // ambiente más usado 
    @Query("""
        SELECT r.ambiente
        FROM Reserva r
        WHERE r.estado= :estado
            AND r.fechaHoraInicio >= :desde
        Group by r.ambiente
        Order by count(r) desc
    """)
    List<Ambiente> findReservasActivasDesde(
        @Param("estado") EstadoReserva estado,
        @Param("desde") LocalDateTime desde
    );

    // Reservas activas 
    @Query("""
        SELECT r FROM Reserva r
        WHERE r.estado = :estado
        AND r.fechaHoraInicio >= :desde
        AND r.fechaHoraInicio < :hasta
    """)
    List<Reserva> findReservasActivasEntreFechas(
        @Param("estado") EstadoReserva estado,
        @Param("desde") LocalDateTime desde,
        @Param("hasta") LocalDateTime hasta
    );
}