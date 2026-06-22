package com.example.Reservas.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Reservas.dtos.ReservaDto;
import com.example.Reservas.services.ReservaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReservaController {
private final ReservaService reservaService;

    @PostMapping("/reservas")
    public ResponseEntity<ReservaDto> crear (@Valid @RequestBody ReservaDto dto){

        ReservaDto creada = reservaService.crear(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PatchMapping ("/reservas/{id}/cancelar")
    public ResponseEntity <ReservaDto> cancelar(@PathVariable Long id){

        ReservaDto cancelada = reservaService.cancelar(id);

        return ResponseEntity.ok(cancelada);
    }

    @GetMapping("/ambientes/{id}/reservas")
    public ResponseEntity<List<ReservaDto>> listarPorAmbienteYFecha(@PathVariable Long id, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(reservaService.listarPorAmbienteYFecha(id, fecha));
    }
}
