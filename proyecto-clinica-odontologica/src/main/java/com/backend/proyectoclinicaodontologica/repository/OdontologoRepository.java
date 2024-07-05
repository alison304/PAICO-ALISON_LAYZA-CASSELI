package com.backend.proyectoclinicaodontologica.repository;

import com.backend.proyectoclinicaodontologica.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OdontologoRepository extends JpaRepository<Odontologo,Long> {
    Odontologo findByMatricula(String matricula);
}

