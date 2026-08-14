package com.david.ApiMesaAyuda.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SolicitudInicioSesion {
    
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El formato del correo no es valido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}
