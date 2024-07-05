package com.backend.proyectoclinicaodontologica.service.impl;

import com.backend.proyectoclinicaodontologica.dto.input.DomicilioDtoInput;
import com.backend.proyectoclinicaodontologica.dto.input.PacienteDtoInput;
import com.backend.proyectoclinicaodontologica.dto.output.PacienteDtoOut;
import com.backend.proyectoclinicaodontologica.exception.DniDuplicadoException;
import com.backend.proyectoclinicaodontologica.exception.ResourceNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PacienteServiceTest {
    @Autowired
    private PacienteService pacienteService;

    @Test
    @Order(1)
    void deberiaRegistrarseUnPacienteDeNombreCass_yRetornarloConSuId() {

        PacienteDtoInput pacienteDtoInput = new PacienteDtoInput("Cass", "Layza", 12345678, LocalDate.of(2024, 6, 27), new DomicilioDtoInput("Los Alamos", 471, "Miraflores", "Lima"));

        PacienteDtoOut pacienteDtoOut = assertDoesNotThrow(() -> {
            return pacienteService.registrarPaciente(pacienteDtoInput);
        });

        assertNotNull(pacienteDtoOut);
        assertNotNull(pacienteDtoOut.getId());
        assertEquals("Cass", pacienteDtoOut.getNombre());
        assertEquals("Lima", pacienteDtoOut.getDomicilio().getProvincia());
        assertEquals(12345678, pacienteDtoOut.getDni());
    }

    @Test
    @Order(2)
    void deberiaDevolverUnaListaNoVaciaDePacientes() {
        List<PacienteDtoOut> listadoDePacientes = pacienteService.listarPacientes();
        assertFalse(listadoDePacientes.isEmpty());
    }

    @Test
    @Order(3)
    void deberiaBuscarUnPacienteDeNombreCass_yRetornarSuId1() {
        PacienteDtoOut pacienteDtoOut = pacienteService.buscarPaciente(1L);
        assertEquals(1L, pacienteDtoOut.getId());
        assertEquals("Cass", pacienteDtoOut.getNombre());
    }

    @Test
    @Order(4)
    void deberiaActulizarLaDireccionDelPacienteConId1() {
        PacienteDtoInput pacienteDtoInput = new PacienteDtoInput("Cass", "Layza", 12345678, LocalDate.of(2024, 6, 27),
                new DomicilioDtoInput("Las Quintanas", 129, "Larco", "Trujillo"));
        PacienteDtoOut pacienteDtoOut = assertDoesNotThrow(() -> {
            return pacienteService.actualizarPaciente(pacienteDtoInput, 1L);
        });
        assertEquals("Las Quintanas", pacienteDtoOut.getDomicilio().getCalle());
        assertEquals(129, pacienteDtoOut.getDomicilio().getNumero());
        assertEquals("Larco", pacienteDtoOut.getDomicilio().getLocalidad());
        assertEquals("Trujillo", pacienteDtoOut.getDomicilio().getProvincia());
    }

    @Test
    @Order(5)
    void deberiaEliminarseElPacienteConId1() {
        assertDoesNotThrow(() -> pacienteService.eliminarPaciente(1L));
    }

    @Test
    @Order(6)
    void deberiaLanzarResourceNotFoundExceptionParaPacienteInexistente() {
        //Id 100L no existe
        Long idInexistente = 100L;
        assertThrows(ResourceNotFoundException.class, () -> pacienteService.eliminarPaciente(idInexistente));
    }

    @Test
    @Order(7)
    void deberiaDevolverUnaListaVaciaDePacientes() {
        List<PacienteDtoOut> listadoDePacientes = pacienteService.listarPacientes();
        assertTrue(listadoDePacientes.isEmpty());
    }

    @Test
    @Order(8)
    void noDeberiaRegistraseUnPacienteConElMismoDni() {
        PacienteDtoInput pacienteDtoInput1 = new PacienteDtoInput("Cass", "Layza", 12345678, LocalDate.of(2024, 6, 27), new DomicilioDtoInput("Los Alamos", 471, "Miraflores", "Lima"));
        PacienteDtoOut pacienteDtoOut1 = assertDoesNotThrow(() -> {
            return pacienteService.registrarPaciente(pacienteDtoInput1);
        });
        assertEquals(12345678, pacienteDtoOut1.getDni());
        // Intento de registrar el mismo paciente otra vez (criterio identificacion unico DNI)
        PacienteDtoInput pacienteDtoInput2 = new PacienteDtoInput("Cass", "Layza", 12345678, LocalDate.of(2024, 6, 27), new DomicilioDtoInput("Los Alamos", 471, "Miraflores", "Lima"));
        assertThrows(DniDuplicadoException.class, () -> pacienteService.registrarPaciente(pacienteDtoInput2));
    }

}
