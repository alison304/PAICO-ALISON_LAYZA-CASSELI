package com.backend.proyectoclinicaodontologica.service;

import com.backend.proyectoclinicaodontologica.dto.input.OdontologoDtoInput;
import com.backend.proyectoclinicaodontologica.dto.output.OdontologoDtoOut;
import com.backend.proyectoclinicaodontologica.exception.MatriculaDuplicadaException;
import com.backend.proyectoclinicaodontologica.exception.ReferentialIntegrityConstraintViolationException;
import com.backend.proyectoclinicaodontologica.exception.ResourceNotFoundException;

import java.util.List;

public interface IOdontologoService {
    OdontologoDtoOut registrarOdontologo(OdontologoDtoInput odontologoDtoInput) throws MatriculaDuplicadaException;

    OdontologoDtoOut buscarOdontologo(Long id);

    List<OdontologoDtoOut> listarOdontologos();

    OdontologoDtoOut actualizarOdontologo(OdontologoDtoInput odontologoDtoInput, Long id) throws ResourceNotFoundException;

    void eliminarOdontologo(Long id) throws ResourceNotFoundException, ReferentialIntegrityConstraintViolationException;


}
