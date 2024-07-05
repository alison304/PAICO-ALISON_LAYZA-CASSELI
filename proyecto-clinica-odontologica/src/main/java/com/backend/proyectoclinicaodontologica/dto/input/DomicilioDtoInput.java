package com.backend.proyectoclinicaodontologica.dto.input;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class DomicilioDtoInput {
    @NotBlank(message = "The value calle should not be empty")
    @Size(max = 50, message = "The value calle should has max 50 characters")
    private String calle;
    @Positive(message = "The value numero should not null or less than zero")
    @Digits(integer = 8, fraction = 0, message = "The value numero should has max 8 digits")
    private int numero;

    @NotBlank(message = "The value localidad should not be empty")
    @Size(max = 50, message = "The value localidad should has max 50 characters")
    private String localidad;

    @NotBlank(message = "The value provincia should not be empty")
    @Size(max = 50, message = "The value provincia should has max 50 characters")
    private String provincia;

    public DomicilioDtoInput() {
    }

    public DomicilioDtoInput(String calle, int numero, String localidad, String provincia) {
        this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;
        this.provincia = provincia;
    }


    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
}
