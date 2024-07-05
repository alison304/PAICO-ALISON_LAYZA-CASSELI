package com.backend.proyectoclinicaodontologica.service;

import com.backend.proyectoclinicaodontologica.dto.input.PacienteDtoInput;
import com.backend.proyectoclinicaodontologica.dto.output.PacienteDtoOut;
import com.backend.proyectoclinicaodontologica.exception.DniDuplicadoException;
import com.backend.proyectoclinicaodontologica.exception.ReferentialIntegrityConstraintViolationException;
import com.backend.proyectoclinicaodontologica.exception.ResourceNotFoundException;

import java.util.List;

public interface IPacienteService {

    PacienteDtoOut registrarPaciente(PacienteDtoInput pacienteDtoInput) throws DniDuplicadoException;

    PacienteDtoOut buscarPaciente(Long id);

    List<PacienteDtoOut> listarPacientes();

    PacienteDtoOut actualizarPaciente(PacienteDtoInput pacienteDtoInput, Long id) throws ResourceNotFoundException;

    void eliminarPaciente(Long id) throws ResourceNotFoundException, ReferentialIntegrityConstraintViolationException;

}
