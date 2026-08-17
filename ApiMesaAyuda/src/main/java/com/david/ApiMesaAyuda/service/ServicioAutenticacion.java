package com.david.ApiMesaAyuda.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.david.ApiMesaAyuda.dto.RespuestaAutenticacion;
import com.david.ApiMesaAyuda.dto.SolicitudInicioSesion;
import com.david.ApiMesaAyuda.dto.SolicitudRegistro;
import com.david.ApiMesaAyuda.entity.RefreshToken;
import com.david.ApiMesaAyuda.entity.Rol;
import com.david.ApiMesaAyuda.entity.Usuario;
import com.david.ApiMesaAyuda.exception.EmailDuplicadoException;
import com.david.ApiMesaAyuda.exception.TokenInvalidoException;
import com.david.ApiMesaAyuda.repository.RefreshTokenRepository;
import com.david.ApiMesaAyuda.repository.UsuarioRepository;
import com.david.ApiMesaAyuda.security.JwtService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicioAutenticacion {
    
    private final UsuarioRepository usuarioRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager autenticationManager;

    @Value("${jwt.refresh-expiration-ms}")
    private long tiempoExpiracionMs;

    @Transactional
    public RespuestaAutenticacion registrar(SolicitudRegistro solicitud){
        
        if (usuarioRepository.existsByEmail(solicitud.getEmail())){
            throw new EmailDuplicadoException(solicitud.getEmail());
        }

        Usuario usuario = Usuario.builder()
            .nombre(solicitud.getNombre())
            .email(solicitud.getEmail())
            .password(passwordEncoder.encode(solicitud.getPassword()))
            .rol(Rol.USUARIO)
            .build();

        usuarioRepository.save(usuario);
        return generarParDeTokens(usuario);

    }

    @Transactional
    public RespuestaAutenticacion login(SolicitudInicioSesion solicitud){
        
        try {
            autenticationManager.authenticate(new UsernamePasswordAuthenticationToken(solicitud.getEmail(), solicitud.getPassword()));
        } catch (org.springframework.security.core.AuthenticationException e) {
            throw new BadCredentialsException("Correo o contraseña incorrectos");
        } 

        Usuario usuario = usuarioRepository.findByEmail(solicitud.getEmail()).orElseThrow(() -> new BadCredentialsException("Correo o contraseña incorrectos"));

            return generarParDeTokens(usuario);
    }

    @Transactional
    public RespuestaAutenticacion refrescar(String refreshTokenValor){

        RefreshToken tokenGuardado = refreshTokenRepository.findByToken(refreshTokenValor).orElseThrow(() -> new TokenInvalidoException("El refresh token no existe"));

        if (!tokenGuardado.esValido()){
            throw new TokenInvalidoException("El refresh tokend esta expirado o fue revocado");
        }

        tokenGuardado.setRevocado(true);
        refreshTokenRepository.save(tokenGuardado);

        Usuario usuario = tokenGuardado.getUsuario();
        String nuevoAccesToken = jwtService.generarAccessToken(usuario);
        RefreshToken nuevoRefreshToken = crearRefreshToken(usuario);

        return RespuestaAutenticacion.builder()
            .tokenAcceso(nuevoAccesToken)
            .tokenRefresco(nuevoRefreshToken.getToken())
            .tipoToken("Bearer")
            .build();
    }

    @Transactional
    public void cerrarSesion(String refreshTokenValor){

        RefreshToken tokenGuardado = refreshTokenRepository.findByToken(refreshTokenValor).orElseThrow(() -> new TokenInvalidoException("El refresh token no existe"));

        tokenGuardado.setRevocado(true);
        refreshTokenRepository.save(tokenGuardado);
    }

    @Transactional
    public RespuestaAutenticacion generarParDeTokens(Usuario usuario){

        String tokenAcceso = jwtService.generarAccessToken(usuario);
        
        RefreshToken refreshToken = crearRefreshToken(usuario);

        return RespuestaAutenticacion.builder()
            .tokenAcceso(tokenAcceso)
            .tokenRefresco(refreshToken.getToken())
            .tipoToken("Barer")
            .build();

    }
    private RefreshToken crearRefreshToken(Usuario usuario) {

        RefreshToken refreshToken = RefreshToken.builder()
            .token(UUID.randomUUID().toString())
            .usuario(usuario)
            .expiraEn(LocalDateTime.now().plus(Duration.ofMillis(tiempoExpiracionMs)))
            .revocado(false)
            .build();
        
            return refreshTokenRepository.save(refreshToken);
    }
}
