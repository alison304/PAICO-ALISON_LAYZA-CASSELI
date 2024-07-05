package com.backend.proyectoclinicaodontologica.dto.input;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;

public class PacienteDtoInput {
    @NotBlank(message = "The value nombre should not be empty")
    @Size(max = 50, message = "The value nombre should has max 50 characters")
    private String nombre;
    @NotBlank(message = "The value apellido should not be empty")
    @Size(max = 50, message = "The value apellido should has max 50 characters")
    private String apellido;
    @Positive(message = "The value dni should not be null or less than zero")
    @Digits(integer = 8, fraction = 0, message = "The value dni should has max 8 digits")
    private int dni;
    @FutureOrPresent(message = " The value fechaIngreso should not be before to today")
    @NotNull(message = "Should has a value")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaIngreso;
    @NotNull(message = "El domicilio of customer has not to be empty")
    @Valid
    private DomicilioDtoInput domicilio;

    public PacienteDtoInput() {
    }

    public PacienteDtoInput(String nombre, String apellido, int dni, LocalDate fechaIngreso, DomicilioDtoInput domicilio) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaIngreso = fechaIngreso;
        this.domicilio = domicilio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public DomicilioDtoInput getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(DomicilioDtoInput domicilio) {
        this.domicilio = domicilio;
    }

/*    @Override
    public String toString() {
        return "nombre: " + nombre + " - apellido: " + apellido + " - dni: " + dni + " - fechaIngreso: " + fechaIngreso + " - domicilio: " + domicilio;
    }*/

}
