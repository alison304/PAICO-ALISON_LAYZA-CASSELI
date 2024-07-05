package com.backend.proyectoclinicaodontologica.service.impl;

import com.backend.proyectoclinicaodontologica.dto.input.PacienteDtoInput;
import com.backend.proyectoclinicaodontologica.dto.output.PacienteDtoOut;
import com.backend.proyectoclinicaodontologica.entity.Paciente;
import com.backend.proyectoclinicaodontologica.entity.Turno;
import com.backend.proyectoclinicaodontologica.exception.DniDuplicadoException;
import com.backend.proyectoclinicaodontologica.exception.ReferentialIntegrityConstraintViolationException;
import com.backend.proyectoclinicaodontologica.exception.ResourceNotFoundException;
import com.backend.proyectoclinicaodontologica.repository.PacienteRepository;
import com.backend.proyectoclinicaodontologica.service.IPacienteService;
import com.backend.proyectoclinicaodontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteService implements IPacienteService {
    private final Logger LOGGER = LoggerFactory.getLogger(PacienteService.class);

    private final PacienteRepository pacienteRepository;
    private final ModelMapper modelMapper;


    public PacienteService(PacienteRepository pacienteRepository, ModelMapper modelMapper) {
        this.pacienteRepository = pacienteRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }


    public PacienteRepository getPacienteRepository() {
        return pacienteRepository;
    }


    @Override
    public PacienteDtoOut registrarPaciente(PacienteDtoInput pacienteDtoInput) throws DniDuplicadoException {
        LOGGER.info("pacienteDtoInput --> {}", JsonPrinter.toString(pacienteDtoInput));

        int dni = pacienteDtoInput.getDni();
        if (pacienteRepository.findByDni(dni) != null)
            throw new DniDuplicadoException("El DNI " + dni + " que intenta registrar ya existe en el sistema");

        Paciente pacienteARegistrar = modelMapper.map(pacienteDtoInput, Paciente.class);
        Paciente pacienteRegistrado = pacienteRepository.save(pacienteARegistrar);
        LOGGER.info("pacienteRegistrado --> {}", JsonPrinter.toString(pacienteRegistrado));

        PacienteDtoOut pacienteDtoOut = modelMapper.map(pacienteRegistrado, PacienteDtoOut.class);
        LOGGER.info("pacienteDtoOut --> {}", JsonPrinter.toString(pacienteDtoOut));

        return pacienteDtoOut;
    }

    @Override
    public PacienteDtoOut buscarPaciente(Long id) {
        Paciente pacienteBuscado = pacienteRepository.findById(id).orElse(null);
        LOGGER.info("pacienteBuscado --> {}", JsonPrinter.toString(pacienteBuscado));

        PacienteDtoOut pacienteDtoOut = null;
        if (pacienteBuscado != null) {
            pacienteDtoOut = modelMapper.map(pacienteBuscado, PacienteDtoOut.class);
            LOGGER.info("Paciente encontrado --> {}", JsonPrinter.toString(pacienteDtoOut));
        } else {
            LOGGER.error("Paciente no encontrado, verificar el id --> {}", id);
        }

        return pacienteDtoOut;
    }

    @Override
    public List<PacienteDtoOut> listarPacientes() {
        List<PacienteDtoOut> pacientes = pacienteRepository.findAll().stream()
                .map(paciente -> modelMapper.map(paciente, PacienteDtoOut.class)).collect(Collectors.toList());
        LOGGER.info("Listado de todos los pacientes --> {}", JsonPrinter.toString(pacientes));
        return pacientes;
    }

    @Override
    public PacienteDtoOut actualizarPaciente(PacienteDtoInput pacienteDtoInput, Long id) throws ResourceNotFoundException {

        LOGGER.info("pacienteDtoInput --> {}", JsonPrinter.toString(pacienteDtoInput));
        LOGGER.info("id input --> {}", id);

        PacienteDtoOut pacienteDtoOut = null;
        Paciente pacienteEncontrado = pacienteRepository.findById(id).orElse(null);

        if (pacienteEncontrado != null) {
            Paciente pacienteAActualizar = modelMapper.map(pacienteDtoInput, Paciente.class);
            pacienteAActualizar.setId(pacienteEncontrado.getId());
            pacienteAActualizar.getDomicilio().setId(pacienteEncontrado.getDomicilio().getId());

            Paciente pacienteActualizado = pacienteRepository.save(pacienteAActualizar);
            LOGGER.info("Paciente actualizado --> {}", JsonPrinter.toString(pacienteActualizado));

            pacienteDtoOut = modelMapper.map(pacienteActualizado, PacienteDtoOut.class);
            LOGGER.info("pacienteDtoOut --> {}", JsonPrinter.toString(pacienteDtoOut));
        } else {
            LOGGER.error("No fue posible actualizar el paciente porque no se encuentra en nuestra base de datos, verificar id {}", id);
            //Custom exception
            throw new ResourceNotFoundException("No existe registro de paciente a actualizar con id: " + id);
        }

        return pacienteDtoOut;
    }

    @Override
    public void eliminarPaciente(Long id) throws ResourceNotFoundException, ReferentialIntegrityConstraintViolationException {
        if (buscarPaciente(id) != null) {
            if (TurnoService.validarSiExisteUnIdPacienteEnLaTablaTurno(id) != null) {
                Turno turnoReferenciado = TurnoService.validarSiExisteUnIdPacienteEnLaTablaTurno(id);
                throw new ReferentialIntegrityConstraintViolationException("No es posible eliminar el paciente con DNI " + turnoReferenciado.getPaciente().getDni() + " porque esta registrado en un turno, elimine los turnos donde esta registrado antes de eliminar este paciente");
            } else {
                pacienteRepository.deleteById(id);
                LOGGER.info("Paciente eliminado, con id --> {}", id);
            }
        } else {
            //Custom exception
            throw new ResourceNotFoundException("No existe registro de paciente con id: " + id);
        }
    }


    private void configureMapping() {
        modelMapper.typeMap(PacienteDtoInput.class, Paciente.class)
                .addMappings(mapper -> mapper.map(PacienteDtoInput::getDomicilio, Paciente::setDomicilio));
        modelMapper.typeMap(Paciente.class, PacienteDtoOut.class)
                .addMappings(mapper -> mapper.map(Paciente::getDomicilio, PacienteDtoOut::setDomicilio));
    }

}
