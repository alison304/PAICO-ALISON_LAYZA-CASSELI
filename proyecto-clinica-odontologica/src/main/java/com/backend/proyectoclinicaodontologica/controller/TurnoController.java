package com.backend.proyectoclinicaodontologica.controller;

import com.backend.proyectoclinicaodontologica.dto.input.TurnoDtoInput;
import com.backend.proyectoclinicaodontologica.dto.output.TurnoDtoOut;
import com.backend.proyectoclinicaodontologica.exception.BadRequestException;
import com.backend.proyectoclinicaodontologica.exception.ResourceNotFoundException;
import com.backend.proyectoclinicaodontologica.service.ITurnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/turnos")
@CrossOrigin
public class TurnoController {

    private ITurnoService turnoService;

    public TurnoController(ITurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<TurnoDtoOut> registrarTurno(@RequestBody @Valid TurnoDtoInput turnoDtoInput) throws BadRequestException {
        return new ResponseEntity<>(turnoService.registrarTurno(turnoDtoInput), HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<TurnoDtoOut>> listarTurnos() {
        return new ResponseEntity<>(turnoService.listarTurnos(),HttpStatus.OK);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<TurnoDtoOut> buscarTurno(@PathVariable Long id) {
        return new ResponseEntity<>(turnoService.buscarTurno(id),HttpStatus.OK);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<TurnoDtoOut> actualizarTurno(@RequestBody  @Valid TurnoDtoInput turnoDtoInput, @PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(turnoService.actualizarTurno(turnoDtoInput, id),HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarTurno(@PathVariable Long id) throws ResourceNotFoundException {
        turnoService.eliminarTurno(id);
        return new ResponseEntity<>("Turno eliminado correctamente",HttpStatus.NO_CONTENT);
    }
    
    
}
