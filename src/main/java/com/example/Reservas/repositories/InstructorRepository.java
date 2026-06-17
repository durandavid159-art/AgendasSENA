package com.example.Reservas.repositories;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Reservas.entities.Instructor;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    @Query ("SELECT COUNT (r) > 0 FROM  Reservar r WHERE r.instructor.id = :instructorId " + "AND r.estado = 'ACTIVA' AND r.fechaHorarioInicio < :fin AND r.fechaHoraFin > :inicio")

    boolean existsOverlappingInstructor(@Param("instrutorId") Long instructorId, @Param ("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
}
