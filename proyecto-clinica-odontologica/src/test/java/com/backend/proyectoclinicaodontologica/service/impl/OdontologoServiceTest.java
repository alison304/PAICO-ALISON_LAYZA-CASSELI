package com.backend.proyectoclinicaodontologica.service.impl;

import com.backend.proyectoclinicaodontologica.dto.input.OdontologoDtoInput;
import com.backend.proyectoclinicaodontologica.dto.output.OdontologoDtoOut;
import com.backend.proyectoclinicaodontologica.exception.MatriculaDuplicadaException;
import com.backend.proyectoclinicaodontologica.exception.ResourceNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OdontologoServiceTest {

    @Autowired
    private OdontologoService odontologoService;

    @Test
    @Order(1)
    void deberiaRegistrarseUnOdontologoDeNombreLuis_yRetornarloConSuId() {

        OdontologoDtoInput odontologoDtoInput = new OdontologoDtoInput("MAT-37895", "Luis", "Lopez");

        OdontologoDtoOut odontologoDtoOut = assertDoesNotThrow(() -> {
            return odontologoService.registrarOdontologo(odontologoDtoInput);
        });

        assertNotNull(odontologoDtoOut);
        assertNotNull(odontologoDtoOut.getId());
        assertEquals("Luis", odontologoDtoOut.getNombre());
        assertEquals("Lopez", odontologoDtoOut.getApellido());
        assertEquals("MAT-37895", odontologoDtoOut.getMatricula());
    }

    @Test
    @Order(2)
    void deberiaDevolverUnaListaNoVaciaDeOdontologos() {
        List<OdontologoDtoOut> listadoDeOdontologos = odontologoService.listarOdontologos();
        assertFalse(listadoDeOdontologos.isEmpty());
    }

    @Test
    @Order(3)
    void deberiaBuscarUnOdontologoDeNombreLuis_yRetornarSuId1() {
        OdontologoDtoOut odontologoDtoOut = odontologoService.buscarOdontologo(1L);
        assertEquals(1L, odontologoDtoOut.getId());
        assertEquals("Luis", odontologoDtoOut.getNombre());
    }

    @Test
    @Order(4)
    void deberiaActulizarLaMatriculaDelOdontologoConId1() {
        OdontologoDtoInput odontologoDtoInput = new OdontologoDtoInput("MAT-22441", "Luis", "Layza");
        OdontologoDtoOut odontologoDtoOut = assertDoesNotThrow(() -> {
            return odontologoService.actualizarOdontologo(odontologoDtoInput, 1L);
        });
        assertEquals("MAT-22441", odontologoDtoOut.getMatricula());
        assertEquals("Luis", odontologoDtoOut.getNombre());
        assertEquals("Layza", odontologoDtoOut.getApellido());

    }

    @Test
    @Order(5)
    void deberiaEliminarseElOdontologoConId1() {
        assertDoesNotThrow(() -> odontologoService.eliminarOdontologo(1L));
    }

    @Test
    @Order(6)
    void deberiaLanzarResourceNotFoundExceptionParaOdontologoInexistenteConId1() {
        //Id 1L ya no existe
        Long idInexistente = 1L;
        assertThrows(ResourceNotFoundException.class, () -> odontologoService.eliminarOdontologo(idInexistente));
    }

    @Test
    @Order(7)
    void deberiaDevolverUnaListaVaciaDeOdontologos() {
        List<OdontologoDtoOut> listadoDeOdontologos = odontologoService.listarOdontologos();
        assertTrue(listadoDeOdontologos.isEmpty());
    }

    @Test
    @Order(8)
    void noDeberiaRegistraseUnOdontologoConLaMismaMatricula() {
        OdontologoDtoInput odontologoDtoInput1 = new OdontologoDtoInput("MAT-37895", "Luis", "Lopez");
        OdontologoDtoOut odontologoDtoOut1 = assertDoesNotThrow(() -> {
            return odontologoService.registrarOdontologo(odontologoDtoInput1);
        });
        assertEquals("MAT-37895", odontologoDtoOut1.getMatricula());
        // Intento de registrar el mismo odontologo otra vez (criterio identificacion unico Matricula)
        OdontologoDtoInput odontologoDtoInput2 = new OdontologoDtoInput("MAT-37895", "Luis", "Lopez");
        assertThrows(MatriculaDuplicadaException.class, () -> odontologoService.registrarOdontologo(odontologoDtoInput2));
    }

}
