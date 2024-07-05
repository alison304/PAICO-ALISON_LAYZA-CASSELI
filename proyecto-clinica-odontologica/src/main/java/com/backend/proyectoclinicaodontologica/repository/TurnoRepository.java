package com.backend.proyectoclinicaodontologica.repository;

import com.backend.proyectoclinicaodontologica.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {
    List<Turno> findByOdontologoId(Long odontologoId);
    List<Turno> findByPacienteId(Long pacienteId);
}
