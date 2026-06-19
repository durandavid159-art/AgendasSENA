package com.example.Reservas.repositories;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Reservas.entities.Reserva;

public interface ReservaRepository extends JpaRepository <Reserva, Long> {

    @Query("SELECT COUNT(r) > 0 FROM Reservas r WHERE r.ambiente.id = :ambienteId" + "AND r.estado = 'ACTIVA' AND r.fechaHoraInicio < :fin AND r.fechaHoraFin > :inicio")

    boolean existsOverlappingReserva(@Param ("ambienteId") long ambienteId, @Param ("inicio") LocalDateTime inicio, @Param ("fin") LocalDateTime fin);
} 