package com.example.Reservas.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Reservas.dtos.AmbienteDto;
import com.example.Reservas.enums.EstadoReserva;
import com.example.Reservas.services.AmbienteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ambientes")
@RequiredArgsConstructor
public class AmbienteController {
    private final AmbienteService ambienteService;

    @PostMapping
    public ResponseEntity <AmbienteDto> crear(@Valid @RequestBody AmbienteDto dto){
        AmbienteDto creado = ambienteService.crear(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    public ResponseEntity <List<AmbienteDto>> listar(){
        return ResponseEntity.ok(ambienteService.listar());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<AmbienteDto>> buscarDisponibles(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime horaInicio,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime horaFin) {
    
    LocalDateTime inicio = fecha.atTime(horaInicio);
    LocalDateTime fin = fecha.atTime(horaFin);
    
    return ResponseEntity.ok(ambienteService.listarDisponibles(EstadoReserva.Activa, inicio, fin));
}
}
