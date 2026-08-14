package com.david.ApiMesaAyuda.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RespuestaAutenticacion {
    
    private String tokenAcceso;
    private String tokenRefresco;

    @Builder.Default
    private String tipoToken = "Bearer";
}
