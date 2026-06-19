package com.example.Reservas.entities;

import java.time.LocalDateTime;

import com.example.Reservas.enums.EstadoReserva;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "reservas")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Reserva {

    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    
    private Long id;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private Integer aprendices;

    @Enumerated(EnumType.STRING)
    private EstadoReserva estado;

    @ManyToOne
    @JoinColumn(name = "ambiente_id", nullable = false)
    private Ambiente ambiente;

    @ManyToOne
    @JoinColumn(name = "instructor_id", nullable = false)
    private Instructor instructor;

}
