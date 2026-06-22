package com.example.Reservas.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Reservas.dtos.AmbienteMasUsadoDto;
import com.example.Reservas.dtos.ReporteOcupacionDto;
import com.example.Reservas.services.ReporteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor 
public class ReporteController {
    private final ReporteService reporteService;

    @GetMapping("/ocupacion")
    public ResponseEntity<List<ReporteOcupacionDto>> reporteOcupacion(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha
    ) {
        return ResponseEntity.ok(reporteService.generarReporteOcupacion(fecha));
    }

    @GetMapping("/ambiente-mas-usado")
    public ResponseEntity<AmbienteMasUsadoDto> ambienteMasUsado() {
        return ResponseEntity.ok(reporteService.generarReporteAmbienteMasUsado());
    }
}
