package com.david.ApiMesaAyuda.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

    @Bean
    public SecurityFilterChain cadenaFiltros(HttpSecurity http) throws Exception{

        http
            .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frame -> frame.disable())) // necesario para H2 console
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(jwtManejadorErrorAutenticacion)
                        .accessDeniedHandler(jwtGestionAccesoDenegado))
                        
                .authorizeHttpRequests(auth -> auth
                        // Publicos
                        .requestMatchers("/api/auth/registro", "/api/auth/login", "/api/auth/refresh", "/api/ping")
                        .permitAll()
                        .requestMatchers("/h2-console/**").permitAll()

                        // ADMIN
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // SOPORTE / ADMIN
                        .requestMatchers(HttpMethod.GET, "/api/tickets/vencidos").hasAnyRole("SOPORTE", "ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/tickets/*/estado").hasAnyRole("SOPORTE", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/tickets").hasAnyRole("SOPORTE", "ADMIN")

                        // Cualquier usuario autenticado
                        .requestMatchers(HttpMethod.GET, "/api/tickets/mios").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/tickets/*").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/tickets").authenticated()
                        .requestMatchers("/api/auth/logout").authenticated()

                        .anyRequest().authenticated())
                .authenticationProvider(proveedorAutenticacion())
                .addFilterBefore(filtroAutenticacionJwt, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
