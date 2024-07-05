package com.backend.proyectoclinicaodontologica.dto.output;

import java.time.LocalDateTime;

public class TurnoDtoOut {

    private Long id;
    private LocalDateTime fechaYHora;
    private OdontologoDtoOut odontologo;
    private PacienteDtoOut paciente;


    public TurnoDtoOut() {
    }

    public TurnoDtoOut(Long id, LocalDateTime fechaYHora, OdontologoDtoOut odontologo, PacienteDtoOut paciente) {
        this.id = id;
        this.fechaYHora = fechaYHora;
        this.odontologo = odontologo;
        this.paciente = paciente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(LocalDateTime fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

    public OdontologoDtoOut getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(OdontologoDtoOut odontologo) {
        this.odontologo = odontologo;
    }

    public PacienteDtoOut getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteDtoOut paciente) {
        this.paciente = paciente;
    }
}
