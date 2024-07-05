package com.backend.proyectoclinicaodontologica.service.impl;

import com.backend.proyectoclinicaodontologica.dto.input.OdontologoDtoInput;
import com.backend.proyectoclinicaodontologica.dto.input.PacienteDtoInput;
import com.backend.proyectoclinicaodontologica.dto.input.TurnoDtoInput;
import com.backend.proyectoclinicaodontologica.dto.output.OdontologoDtoOut;
import com.backend.proyectoclinicaodontologica.dto.output.PacienteDtoOut;
import com.backend.proyectoclinicaodontologica.dto.output.TurnoDtoOut;
import com.backend.proyectoclinicaodontologica.exception.ResourceNotFoundException;
import com.backend.proyectoclinicaodontologica.testutildata.TestDataUtil;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TurnoServiceTest {
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private OdontologoService odontologoService;

    @Test
    @Order(1)
    void deberiaRegistrarUnTurnoConUnPacienteConDni_yUnOdontologoConMatriculaExistentes() {
        PacienteDtoInput pacienteDtoInput = TestDataUtil.createPacienteDtoInput();
        OdontologoDtoInput odontologoDtoInput = TestDataUtil.createOdontologoDtoInput();

        PacienteDtoOut pacienteDtoOut = assertDoesNotThrow(() -> {
            return pacienteService.registrarPaciente(pacienteDtoInput);

        });
        OdontologoDtoOut odontologoDtoOut = assertDoesNotThrow(() -> {
            return odontologoService.registrarOdontologo(odontologoDtoInput);

        });

        TurnoDtoInput turnoDtoInput = TestDataUtil.createTurnoDtoInput(pacienteDtoInput, odontologoDtoInput);
        TurnoDtoOut turnoDtoOut = assertDoesNotThrow(() -> {
            return turnoService.registrarTurno(turnoDtoInput);
        });

        assertEquals(12345678, turnoDtoOut.getPaciente().getDni());
        assertEquals("MAT-37895", turnoDtoOut.getOdontologo().getMatricula());
        assertNotNull(turnoDtoOut);
        assertNotNull(turnoDtoOut.getId());

    }

    @Test
    @Order(2)
    void deberiaDevolverUnaListaNoVaciaDeTurnos() {
        List<TurnoDtoOut> listadoDeTurnos = turnoService.listarTurnos();
        assertFalse(listadoDeTurnos.isEmpty());
    }

    @Test
    @Order(3)
    void deberiaBuscarUnTurno_yRetornarSuId1() {
        TurnoDtoOut turnoDtoOut = turnoService.buscarTurno(1L);
        assertEquals(1L, turnoDtoOut.getId());
        assertEquals("Cass", turnoDtoOut.getPaciente().getNombre());
        assertEquals(12345678, turnoDtoOut.getPaciente().getDni());
        assertEquals("Luis", turnoDtoOut.getOdontologo().getNombre());
        assertEquals("MAT-37895", turnoDtoOut.getOdontologo().getMatricula());
    }

    @Test
    @Order(4)
    void deberiaActulizarLaFehcaHoraDeUnTurnoConId1() {
        PacienteDtoInput pacienteDtoInput = TestDataUtil.createPacienteDtoInput();
        OdontologoDtoInput odontologoDtoInput = TestDataUtil.createOdontologoDtoInput();
        TurnoDtoInput turnoDtoInput = new TurnoDtoInput(LocalDateTime.of(2024, 10, 17, 11, 6, 0), odontologoDtoInput, pacienteDtoInput);
        TurnoDtoOut turnoDtoOut = assertDoesNotThrow(() -> {
            return turnoService.actualizarTurno(turnoDtoInput, 1L);
        });
        assertEquals("2024-10-17T11:06", String.valueOf(turnoDtoOut.getFechaYHora()));
        assertEquals(1L, turnoDtoOut.getId());
    }

    @Test
    @Order(5)
    void deberiaEliminarseElTurnoConId1() {
        assertDoesNotThrow(() -> turnoService.eliminarTurno(1L));
    }

    @Test
    @Order(6)
    void deberiaLanzarResourceNotFoundExceptionParaTurnoInexistente() {
        //Id 200L no existe
        Long idInexistente = 200L;
        assertThrows(ResourceNotFoundException.class, () -> turnoService.eliminarTurno(idInexistente));
    }

    @Test
    @Order(7)
    void deberiaDevolverUnaListaVaciaDeTurnos() {
        List<TurnoDtoOut> listadoDeTurnos = turnoService.listarTurnos();
        assertTrue(listadoDeTurnos.isEmpty());
    }

}