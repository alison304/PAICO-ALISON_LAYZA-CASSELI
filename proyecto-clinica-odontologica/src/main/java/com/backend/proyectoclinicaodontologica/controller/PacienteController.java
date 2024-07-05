package com.backend.proyectoclinicaodontologica.controller;

import com.backend.proyectoclinicaodontologica.dto.input.PacienteDtoInput;
import com.backend.proyectoclinicaodontologica.dto.output.PacienteDtoOut;
import com.backend.proyectoclinicaodontologica.exception.DniDuplicadoException;
import com.backend.proyectoclinicaodontologica.exception.ReferentialIntegrityConstraintViolationException;
import com.backend.proyectoclinicaodontologica.exception.ResourceNotFoundException;
import com.backend.proyectoclinicaodontologica.service.IPacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pacientes")
@CrossOrigin
public class PacienteController {
    private IPacienteService pacienteService;

    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<PacienteDtoOut> registrarPaciente(@RequestBody @Valid PacienteDtoInput pacienteDtoInput) throws DniDuplicadoException {
        return new ResponseEntity<>(pacienteService.registrarPaciente(pacienteDtoInput), HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PacienteDtoOut>> listarPacientes() {
        return new ResponseEntity<>(pacienteService.listarPacientes(),HttpStatus.OK);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<PacienteDtoOut> buscarPaciente(@PathVariable Long id) {
        return new ResponseEntity<>(pacienteService.buscarPaciente(id),HttpStatus.OK);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<PacienteDtoOut> actualizarPaciente(@RequestBody  @Valid PacienteDtoInput pacienteDtoInput, @PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(pacienteService.actualizarPaciente(pacienteDtoInput, id),HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Long id) throws ResourceNotFoundException, ReferentialIntegrityConstraintViolationException {
        pacienteService.eliminarPaciente(id);
        return new ResponseEntity<>("Paciente eliminado correctamente",HttpStatus.NO_CONTENT);
    }


}
