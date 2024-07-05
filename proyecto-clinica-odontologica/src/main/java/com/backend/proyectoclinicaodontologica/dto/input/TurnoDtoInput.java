package com.backend.proyectoclinicaodontologica.dto.input;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class TurnoDtoInput {
    @NotNull(message = "fechaYHora should has a value")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaYHora;
    @NotNull(message = "Odontologo should not be empty")
    @Valid
    private OdontologoDtoInput odontologo;
    @NotNull(message = "Paciente should not be empty")
    @Valid
    private PacienteDtoInput paciente;


    public TurnoDtoInput() {
    }

    public TurnoDtoInput(LocalDateTime fechaYHora, OdontologoDtoInput odontologo, PacienteDtoInput paciente) {
        this.fechaYHora = fechaYHora;
        this.odontologo = odontologo;
        this.paciente = paciente;
    }

    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(LocalDateTime fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

    public OdontologoDtoInput getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(OdontologoDtoInput odontologo) {
        this.odontologo = odontologo;
    }

    public PacienteDtoInput getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteDtoInput paciente) {
        this.paciente = paciente;
    }
}
