package com.example.Reservas.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Reservas.entities.Ambiente;

public interface AmbienteRepository extends JpaRepository <Ambiente, Long>{
    
    @Query ("SELECT a FROM Ambiente a WHERE a.activo = true AND a.id NOT IN (" +
           "SELECT r.ambiente.id FROM Reserva r WHERE r.estado = 'ACTIVA' AND " +
           "r.fechaHoraInicio < :fin AND r.fechaHoraFin > :inicio)")
           
    List<Ambiente> findAmbientesDisponibles(@Param("inicio") LocalDateTime inicio, @Param("fin")LocalDateTime fin);
}
