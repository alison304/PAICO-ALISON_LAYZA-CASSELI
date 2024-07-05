package com.backend.proyectoclinicaodontologica.service.impl;

import com.backend.proyectoclinicaodontologica.dto.input.TurnoDtoInput;
import com.backend.proyectoclinicaodontologica.dto.output.TurnoDtoOut;
import com.backend.proyectoclinicaodontologica.entity.Odontologo;
import com.backend.proyectoclinicaodontologica.entity.Paciente;
import com.backend.proyectoclinicaodontologica.entity.Turno;
import com.backend.proyectoclinicaodontologica.exception.BadRequestException;
import com.backend.proyectoclinicaodontologica.exception.ResourceNotFoundException;
import com.backend.proyectoclinicaodontologica.repository.TurnoRepository;
import com.backend.proyectoclinicaodontologica.service.ITurnoService;
import com.backend.proyectoclinicaodontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TurnoService implements ITurnoService {

    private final Logger LOGGER = LoggerFactory.getLogger(PacienteService.class);

    private static TurnoRepository turnoRepository;
    private final PacienteService pacienteService;
    private final OdontologoService odontologoService;
    private final ModelMapper modelMapper;


    public TurnoService(TurnoRepository turnoRepository, PacienteService pacienteService, OdontologoService odontologoService, ModelMapper modelMapper) {
        this.turnoRepository = turnoRepository;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
        this.modelMapper = modelMapper;
        configureMapping();
    }



    @Override
    public TurnoDtoOut registrarTurno(TurnoDtoInput turnoDtoInput) throws BadRequestException {

        LOGGER.info("turnoDtoInput --> {}", JsonPrinter.toString(turnoDtoInput));
        Turno turnoRecibido = modelMapper.map(turnoDtoInput, Turno.class);
        Paciente pacienteBuscado = pacienteService.getPacienteRepository().findByDni(turnoRecibido.getPaciente().getDni());
        Odontologo odontologoBuscado = odontologoService.getOdontologoRepository().findByMatricula(turnoRecibido.getOdontologo().getMatricula());
        LOGGER.info("pacienteBuscado --> {}", JsonPrinter.toString(pacienteBuscado));
        LOGGER.info("odontologoBuscado --> {}", JsonPrinter.toString(odontologoBuscado));

        if (pacienteBuscado == null && odontologoBuscado == null) {
            LOGGER.error("No fue posible registrar el turno porque el Paciente y el Odontolog no se encuentran en nuestra base de datos");
            throw new BadRequestException("El Paciente y el Odontologo no existen en la base de datos");
        }
        if (pacienteBuscado == null) {
            LOGGER.error("No fue posible registrar el turno porque el Paciente no se encuentra en nuestra base de datos");
            throw new BadRequestException("El Paciente no existe en la base de datos");
        }
        if (odontologoBuscado == null) {
            LOGGER.error("No fue posible registrar el turno porque el Odontologo no se encuentra en nuestra base de datos");
            throw new BadRequestException("El Odontologo no existe en la base de datos");
        }

        turnoRecibido.getOdontologo().setId(odontologoBuscado.getId());
        turnoRecibido.getPaciente().setId(pacienteBuscado.getId());
        turnoRecibido.getPaciente().getDomicilio().setId(pacienteBuscado.getDomicilio().getId());
        Turno turnoRegistrado = turnoRepository.save(turnoRecibido);
        LOGGER.info("turnoRegistrado --> {}", JsonPrinter.toString(turnoRegistrado));

        TurnoDtoOut turnoDtoOut = modelMapper.map(turnoRegistrado, TurnoDtoOut.class);
        LOGGER.info("turnoDtoOut --> {}", JsonPrinter.toString(turnoDtoOut));

        return turnoDtoOut;
    }

    @Override
    public TurnoDtoOut buscarTurno(Long id) {

        Turno turnoBuscado = turnoRepository.findById(id).orElse(null);
        LOGGER.info("turnoBuscado --> {}", JsonPrinter.toString(turnoBuscado));

        TurnoDtoOut turnoDtoOut = null;
        if (turnoBuscado != null) {
            turnoDtoOut = modelMapper.map(turnoBuscado, TurnoDtoOut.class);
            LOGGER.info("Turno encontrado --> {}", JsonPrinter.toString(turnoDtoOut));
        } else {
            LOGGER.info("Turno no encontrado, verificar el id --> {}", id);
        }

        return turnoDtoOut;
    }

    @Override
    public List<TurnoDtoOut> listarTurnos() {
        List<TurnoDtoOut> turnos = turnoRepository.findAll().stream()
                .map(turno -> modelMapper.map(turno, TurnoDtoOut.class)).collect(Collectors.toList());
        LOGGER.info("Listado de todos los turnos: {}", JsonPrinter.toString(turnos));
        return turnos;
    }

    @Override
    public TurnoDtoOut actualizarTurno(TurnoDtoInput turnoDtoInput, Long id) throws ResourceNotFoundException {
        LOGGER.info("turnoDtoInput --> {}", JsonPrinter.toString(turnoDtoInput));
        LOGGER.info("id input --> {}", id);

        TurnoDtoOut turnoDtoOut = null;
        Turno turnoEncontrado = turnoRepository.findById(id).orElse(null);

        if (turnoEncontrado != null) {
            Turno turnoAActualizar = modelMapper.map(turnoDtoInput, Turno.class);
            turnoAActualizar.setId(turnoEncontrado.getId());
            turnoAActualizar.getOdontologo().setId(turnoEncontrado.getOdontologo().getId());
            turnoAActualizar.getPaciente().setId(turnoEncontrado.getPaciente().getId());
            LOGGER.info("turnoAActualizar3 --> {}", JsonPrinter.toString(turnoAActualizar));
            turnoAActualizar.getPaciente().getDomicilio().setId(turnoEncontrado.getPaciente().getDomicilio().getId());
            Turno turnoActualizado = turnoRepository.save(turnoAActualizar);
            LOGGER.info("Turno actualizado --> {}", JsonPrinter.toString(turnoActualizado));

            turnoDtoOut = modelMapper.map(turnoActualizado, TurnoDtoOut.class);
            LOGGER.info("TurnoDtoOut --> {}", JsonPrinter.toString(turnoDtoOut));

        } else {
            LOGGER.error("No fue posible actualizar el turno porque no se encuentra en nuestra base de datos verificar id {}", id);
            //Custom exception
            throw new ResourceNotFoundException("No existe registro de turno a actualizar con id " + id);
        }

        return turnoDtoOut;
    }

    @Override
    public void eliminarTurno(Long id) throws ResourceNotFoundException {
        if (buscarTurno(id) != null) {
            turnoRepository.deleteById(id);
            LOGGER.info("Turno eliminado con id --> {}", id);
        } else {
            //Custom exception
            throw new ResourceNotFoundException("No existe registro de turno con id " + id);

        }
    }


    private void configureMapping() {
        modelMapper.typeMap(TurnoDtoInput.class, Turno.class)
                .addMappings(mapper -> mapper.map(TurnoDtoInput::getOdontologo, Turno::setOdontologo));
        modelMapper.typeMap(Turno.class, TurnoDtoOut.class)
                .addMappings(mapper -> mapper.map(Turno::getOdontologo, TurnoDtoOut::setOdontologo));

        modelMapper.typeMap(TurnoDtoInput.class, Turno.class)
                .addMappings(mapper -> mapper.map(TurnoDtoInput::getPaciente, Turno::setPaciente));
        modelMapper.typeMap(Turno.class, TurnoDtoOut.class)
                .addMappings(mapper -> mapper.map(Turno::getPaciente, TurnoDtoOut::setPaciente));
    }


    public static Turno validarSiExisteUnIdPacienteEnLaTablaTurno(Long idPaciente) {
        List<Turno> turnos = turnoRepository.findByPacienteId(idPaciente);
        return turnos.size() != 0 ? turnos.get(0) : null;

    }

    public static Turno validarSiExisteUnIdOdontologoEnLaTablaTurno(Long idOdontologo) {
        List<Turno> turnos = turnoRepository.findByOdontologoId(idOdontologo);
        return turnos.size() != 0 ? turnos.get(0) : null;

    }


}
