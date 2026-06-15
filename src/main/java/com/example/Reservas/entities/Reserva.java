package com.example.Reservas.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
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
    
    private long id;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Integer aprendices;
    private String estado;

    @ManyToOne
    @JoinColumn(name = "ambiente_id", nullable = false)
    private Ambiente ambiente;

    @ManyToOne
    @JoinColumn(name = "instructor_id", nullable = false)
    private Instructor instructor;

}
