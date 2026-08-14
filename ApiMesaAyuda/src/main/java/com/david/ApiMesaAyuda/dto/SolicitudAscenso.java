package com.david.ApiMesaAyuda.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SolicitudAscenso {
    
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es valido")
    public String email;
}
