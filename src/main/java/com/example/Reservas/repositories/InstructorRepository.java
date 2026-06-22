package com.example.Reservas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Reservas.entities.Instructor;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {

}
