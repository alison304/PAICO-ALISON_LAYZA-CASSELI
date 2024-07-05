package com.backend.proyectoclinicaodontologica.dto.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class OdontologoDtoInput {
    @NotBlank(message = "The value matricula should not be empty")
    @Pattern(regexp = "MAT-\\d{5}", message = "The matricula format should be MAT- and 5 digits, for instance MAT-75624")
    @Size(max = 9, message = "The value matricula should has max 9 characters")
    private String matricula;

    @NotBlank(message = "The value nombre should not be empty")
    @Size(max = 50, message = "The value nombre should has max 50 characters")
    private String nombre;

    @NotBlank(message = "The value apellido should not be empty")
    @Size(max = 50, message = "The value apellido should has max 50 characters")
    private String apellido;


    public OdontologoDtoInput() {
    }

    public OdontologoDtoInput(String matricula, String nombre, String apellido) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellido = apellido;
    }


    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
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
}
