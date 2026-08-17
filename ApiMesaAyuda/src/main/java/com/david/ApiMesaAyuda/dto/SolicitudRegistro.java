package com.david.ApiMesaAyuda.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SolicitudRegistro {
    
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es valido")
    private String email;

    @NotBlank(message = "La password es obligatoria")
    @Size(min = 6, message = "La password debe tener al menos 6 caracteres")
    private String password;
}
