package com.example.Reservas.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Reservas.dtos.AmbienteDto;
import com.example.Reservas.entities.Ambiente;
import com.example.Reservas.enums.EstadoReserva;
import com.example.Reservas.exception.ExcepcionRecursoNoEncontrado;
import com.example.Reservas.repositories.AmbienteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AmbienteService {

    private final AmbienteRepository ambienteRepository;

    public AmbienteDto crear(AmbienteDto dto) {
        Ambiente ambiente = Ambiente.builder()
                .nombre(dto.getNombre())
                .tipo(dto.getTipo())
                .capacidad(dto.getCapacidad())
                .activo(dto.isActivo())
                .build();

        Ambiente guardado = ambienteRepository.save(ambiente);
        return convertirADto(guardado);
    }

    public List<AmbienteDto> listar() {
        return ambienteRepository.findAll()
                .stream()
                .map(this::convertirADto)
                .toList();
    }

    public Ambiente obtenerPorId(Long id) {
        return ambienteRepository.findById(id)
                .orElseThrow(() -> new ExcepcionRecursoNoEncontrado("Ambiente no encontrado con id: " + id));
    }

    public List<AmbienteDto> listarDisponibles(EstadoReserva estado, LocalDateTime inicio, LocalDateTime fin) {
        return ambienteRepository.findAmbientesDisponibles(estado, inicio, fin)
                .stream()
                .map(this::convertirADto)
                .toList();
    }

    private AmbienteDto convertirADto(Ambiente ambiente) {
        AmbienteDto dto = new AmbienteDto();
        dto.setId(ambiente.getId());
        dto.setNombre(ambiente.getNombre());
        dto.setTipo(ambiente.getTipo());
        dto.setCapacidad(ambiente.getCapacidad());
        dto.setActivo(ambiente.isActivo());
        return dto;
    }
}
