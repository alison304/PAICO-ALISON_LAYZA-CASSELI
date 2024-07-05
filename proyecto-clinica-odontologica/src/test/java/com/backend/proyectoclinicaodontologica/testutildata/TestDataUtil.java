package com.backend.proyectoclinicaodontologica.testutildata;

import com.backend.proyectoclinicaodontologica.dto.input.DomicilioDtoInput;
import com.backend.proyectoclinicaodontologica.dto.input.OdontologoDtoInput;
import com.backend.proyectoclinicaodontologica.dto.input.PacienteDtoInput;
import com.backend.proyectoclinicaodontologica.dto.input.TurnoDtoInput;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestDataUtil {

    public static PacienteDtoInput createPacienteDtoInput() {
        return new PacienteDtoInput("Cass", "Layza", 12345678, LocalDate.of(2024, 6, 27), new DomicilioDtoInput("Los Alamos", 471, "Miraflores", "Lima"));
    }

    public static OdontologoDtoInput createOdontologoDtoInput() {
        return new OdontologoDtoInput("MAT-37895", "Luis", "Lopez");
    }


    public static TurnoDtoInput createTurnoDtoInput(PacienteDtoInput pacienteDtoInput, OdontologoDtoInput odontologoDtoInput) {
        return new TurnoDtoInput(LocalDateTime.of(2024, 6, 27, 11, 11, 0), odontologoDtoInput, pacienteDtoInput);
    }
}