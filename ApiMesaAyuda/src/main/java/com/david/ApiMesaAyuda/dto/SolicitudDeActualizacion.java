package com.david.ApiMesaAyuda.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SolicitudDeActualizacion {
    
    @NotBlank(message = "El token de actualización es obligatorio")
    private String tokenActualizacion;
}
