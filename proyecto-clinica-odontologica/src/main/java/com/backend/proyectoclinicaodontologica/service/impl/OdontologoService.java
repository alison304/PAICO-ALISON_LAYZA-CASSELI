package com.backend.proyectoclinicaodontologica.service.impl;

import com.backend.proyectoclinicaodontologica.dto.input.OdontologoDtoInput;
import com.backend.proyectoclinicaodontologica.dto.output.OdontologoDtoOut;
import com.backend.proyectoclinicaodontologica.entity.Odontologo;
import com.backend.proyectoclinicaodontologica.entity.Turno;
import com.backend.proyectoclinicaodontologica.exception.MatriculaDuplicadaException;
import com.backend.proyectoclinicaodontologica.exception.ReferentialIntegrityConstraintViolationException;
import com.backend.proyectoclinicaodontologica.exception.ResourceNotFoundException;
import com.backend.proyectoclinicaodontologica.repository.OdontologoRepository;
import com.backend.proyectoclinicaodontologica.service.IOdontologoService;
import com.backend.proyectoclinicaodontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OdontologoService implements IOdontologoService {
    private final Logger LOGGER = LoggerFactory.getLogger(OdontologoService.class);

    private final OdontologoRepository odontologoRepository;
    private final ModelMapper modelMapper;

    public OdontologoService(OdontologoRepository odontologoRepository, ModelMapper modelMapper) {
        this.odontologoRepository = odontologoRepository;
        this.modelMapper = modelMapper;

    }

    public OdontologoRepository getOdontologoRepository() {
        return odontologoRepository;
    }

    @Override
    public OdontologoDtoOut registrarOdontologo(OdontologoDtoInput odontologoDtoInput) throws MatriculaDuplicadaException {
        LOGGER.info("odontologoDtoInput --> {} ", JsonPrinter.toString(odontologoDtoInput));

        String matricula = odontologoDtoInput.getMatricula();
        if (odontologoRepository.findByMatricula(matricula) != null)
            throw new MatriculaDuplicadaException("La matricula " + matricula + " que intenta registrar ya existe en el sistema");

        Odontologo odontologoARegistrar = modelMapper.map(odontologoDtoInput, Odontologo.class);
        Odontologo odontologoRegistrado = odontologoRepository.save(odontologoARegistrar);
        LOGGER.info("odontologoRegistrado --> {} ", JsonPrinter.toString(odontologoRegistrado));

        OdontologoDtoOut odontologoDtoOut = modelMapper.map(odontologoRegistrado, OdontologoDtoOut.class);
        LOGGER.info("odontologoDtoOut --> {} ", JsonPrinter.toString(odontologoDtoOut));

        return odontologoDtoOut;
    }

    @Override
    public OdontologoDtoOut buscarOdontologo(Long id) {

        Odontologo odontologoBuscado = odontologoRepository.findById(id).orElse(null);
        LOGGER.info("Odontologo buscado --> {}", JsonPrinter.toString(odontologoBuscado));

        OdontologoDtoOut odontologoDtoOut = null;
        if (odontologoBuscado != null) {
            odontologoDtoOut = modelMapper.map(odontologoBuscado, OdontologoDtoOut.class);
            LOGGER.info("Odontologo encontrado --> {}", JsonPrinter.toString(odontologoDtoOut));
        } else {
            LOGGER.info("Odontologo no encontrado, verificar el id --> {} ", id);
        }

        return odontologoDtoOut;

    }

    @Override
    public List<OdontologoDtoOut> listarOdontologos() {
        List<OdontologoDtoOut> odontologos = odontologoRepository.findAll().stream()
                .map(odontologo -> modelMapper.map(odontologo, OdontologoDtoOut.class)).collect(Collectors.toList());
        LOGGER.info("Listado de todos los odontologos: {}", JsonPrinter.toString(odontologos));
        return odontologos;
    }

    @Override
    public OdontologoDtoOut actualizarOdontologo(OdontologoDtoInput odontologoDtoInput, Long id) throws ResourceNotFoundException {

        LOGGER.info("ontologoDtoInput --> {}", JsonPrinter.toString(odontologoDtoInput));
        LOGGER.info("id input --> {}", id);

        OdontologoDtoOut odontologoDtoOut = null;
        Odontologo odontologoEncontrado = odontologoRepository.findById(id).orElse(null);

        if (odontologoEncontrado != null) {
            Odontologo odontologoAActualizar = modelMapper.map(odontologoDtoInput, Odontologo.class);
            odontologoAActualizar.setId(odontologoEncontrado.getId());

            Odontologo odontologoActualizado = odontologoRepository.save(odontologoAActualizar);
            LOGGER.info("Odontologo actualizado --> {} ", JsonPrinter.toString(odontologoActualizado));

            odontologoDtoOut = modelMapper.map(odontologoActualizado, OdontologoDtoOut.class);
            LOGGER.info("odontologoDtoOut --> {} ", JsonPrinter.toString(odontologoDtoOut));

        } else {
            LOGGER.error("No fue posible actualizar el odontologo porque no se encuentra en nuestra base de datos verificar id {}", id);
            //Custom exception
            throw new ResourceNotFoundException("No existe registro de un odontologo a actualizar con id: " + id);
        }

        return odontologoDtoOut;
    }

    @Override
    public void eliminarOdontologo(Long id) throws ResourceNotFoundException, ReferentialIntegrityConstraintViolationException {
        if (buscarOdontologo(id) != null) {
            if (TurnoService.validarSiExisteUnIdOdontologoEnLaTablaTurno(id) != null) {
                Turno turnoReferenciado = TurnoService.validarSiExisteUnIdOdontologoEnLaTablaTurno(id);
                throw new ReferentialIntegrityConstraintViolationException("No es posible eliminar el odontologo con Matricula " + turnoReferenciado.getOdontologo().getMatricula() + " porque esta registrado en un turno, elimine los turnos donde esta registrado antes de eliminar este odontologo");
            } else {
                odontologoRepository.deleteById(id);
                LOGGER.info("Odontologo eliminado con id --> {}", id);
            }
        } else {
            //Custom exception
            throw new ResourceNotFoundException("No existe registro de un odontologo con id: " + id);

        }

    }
}
