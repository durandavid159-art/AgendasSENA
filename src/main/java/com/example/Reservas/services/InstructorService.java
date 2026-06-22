package com.example.Reservas.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Reservas.dtos.InstructorDto;
import com.example.Reservas.entities.Instructor;
import com.example.Reservas.exception.ExcepcionRecursoNoEncontrado;
import com.example.Reservas.repositories.InstructorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InstructorService {

    private final InstructorRepository instructorRepository;

    public InstructorDto crear(InstructorDto dto) {
        Instructor instructor = Instructor.builder()
                .nombre(dto.getNombre())
                .telefono(dto.getTelefono())
                .build();

        Instructor guardado = instructorRepository.save(instructor);
        return convertirADto(guardado);
    }

    public List<InstructorDto> listar() {
        return instructorRepository.findAll()
                .stream()
                .map(this::convertirADto)
                .toList();
    }

    public Instructor obtenerPorId(Long id) {
        return instructorRepository.findById(id)
                .orElseThrow(() -> new ExcepcionRecursoNoEncontrado("Instructor no encontrado con id: " + id));
    }

    private InstructorDto convertirADto(Instructor instructor) {
        
        InstructorDto dto = new InstructorDto();
        
        dto.setId(instructor.getId());
        dto.setNombre(instructor.getNombre());
        dto.setTelefono(instructor.getTelefono());
        return dto;
    }
}
