package com.david.ApiMesaAyuda.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.david.ApiMesaAyuda.security.FiltroAutenticacionJwt;
import com.david.ApiMesaAyuda.security.JwtGestionAccesoDenegado;
import com.david.ApiMesaAyuda.security.JwtManejadorErrorAutenticacion;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfiguracionSeguridad {
    
    private final FiltroAutenticacionJwt filtroAutenticacionJwt;
    private final JwtManejadorErrorAutenticacion jwtManejadorErrorAutenticacion;
    private final JwtGestionAccesoDenegado jwtGestionAccesoDenegado;
    private final UserDetailsService servicioDetallesUsuario;

    @Bean
    public PasswordEncoder codificadorContrasenas() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider proveedorAutenticacion() {
        DaoAuthenticationProvider proveedor = new DaoAuthenticationProvider(servicioDetallesUsuario);
        proveedor.setPasswordEncoder(codificadorContrasenas());
        return proveedor;
    }

    @Bean
    public AuthenticationManager administradorAutenticacion(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
