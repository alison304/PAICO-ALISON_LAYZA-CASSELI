package com.backend.proyectoclinicaodontologica.controller;

import com.backend.proyectoclinicaodontologica.dto.input.OdontologoDtoInput;
import com.backend.proyectoclinicaodontologica.dto.output.OdontologoDtoOut;
import com.backend.proyectoclinicaodontologica.exception.MatriculaDuplicadaException;
import com.backend.proyectoclinicaodontologica.exception.ReferentialIntegrityConstraintViolationException;
import com.backend.proyectoclinicaodontologica.exception.ResourceNotFoundException;
import com.backend.proyectoclinicaodontologica.service.IOdontologoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/odontologos")
@CrossOrigin
public class OdontologoController {
    private IOdontologoService odontologoService;

    public OdontologoController(IOdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<OdontologoDtoOut> registrarOdotologo(@RequestBody @Valid OdontologoDtoInput odontologoDtoInput) throws MatriculaDuplicadaException {
        return new ResponseEntity<>(odontologoService.registrarOdontologo(odontologoDtoInput), HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<OdontologoDtoOut>> listarOdontologos() {
        return new ResponseEntity<>(odontologoService.listarOdontologos(), HttpStatus.OK);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<OdontologoDtoOut> buscarOdontologo(@PathVariable Long id) {
        return new ResponseEntity<>(odontologoService.buscarOdontologo(id), HttpStatus.OK);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<OdontologoDtoOut> actualizarOdontologo(@RequestBody @Valid OdontologoDtoInput odontologoDtoInput, @PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(odontologoService.actualizarOdontologo(odontologoDtoInput, id), HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarOdontologo(@PathVariable Long id) throws ResourceNotFoundException, ReferentialIntegrityConstraintViolationException {
        odontologoService.eliminarOdontologo(id);
        return new ResponseEntity<>("Odontologo eliminado correctamente", HttpStatus.NO_CONTENT);
    }
}
