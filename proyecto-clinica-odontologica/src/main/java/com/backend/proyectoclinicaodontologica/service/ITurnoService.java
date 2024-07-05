package com.backend.proyectoclinicaodontologica.service;

import com.backend.proyectoclinicaodontologica.dto.input.TurnoDtoInput;
import com.backend.proyectoclinicaodontologica.dto.output.TurnoDtoOut;
import com.backend.proyectoclinicaodontologica.exception.BadRequestException;
import com.backend.proyectoclinicaodontologica.exception.ResourceNotFoundException;

import java.util.List;

public interface ITurnoService {

    TurnoDtoOut registrarTurno(TurnoDtoInput turnoDtoInput) throws BadRequestException;

    TurnoDtoOut buscarTurno(Long id);

    List<TurnoDtoOut> listarTurnos();

    TurnoDtoOut actualizarTurno(TurnoDtoInput turnoDtoInput, Long id) throws ResourceNotFoundException;

    void eliminarTurno(Long id) throws ResourceNotFoundException;

}
