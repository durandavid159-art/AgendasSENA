package com.example.Reservas.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "ambientes")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Ambiente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    
    private long id;
    private String tipo;
    private Integer capacidad;
    private boolean activo;
}
