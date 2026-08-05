package com.david.ApiMesaAyuda.config;

import org.springframework.context.annotation.Configuration;

import com.david.ApiMesaAyuda.security.FiltroAutenticacionJwt;
import com.david.ApiMesaAyuda.security.JwtManejadorErrorAutenticacion;

@Configuration
public class ConfiguracionSeguridad {
    
    private final FiltroAutenticacionJwt filtroAutenticacionJwt;
    private final JwtManejadorErrorAutenticacion jwtManejodorErrorAutenticacion;
}
