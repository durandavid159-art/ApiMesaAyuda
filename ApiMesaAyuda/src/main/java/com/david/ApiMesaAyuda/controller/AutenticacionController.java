package com.david.ApiMesaAyuda.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.david.ApiMesaAyuda.dto.RespuestaAutenticacion;
import com.david.ApiMesaAyuda.dto.SolicitudDeActualizacion;
import com.david.ApiMesaAyuda.dto.SolicitudInicioSesion;
import com.david.ApiMesaAyuda.dto.SolicitudRegistro;
import com.david.ApiMesaAyuda.service.ServicioAutenticacion;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AutenticacionController {
    
    private final ServicioAutenticacion servicioAutenticacion;

    @PostMapping("/registro")
    public ResponseEntity<RespuestaAutenticacion> registrar (@Valid @RequestBody SolicitudRegistro solicitud){

        RespuestaAutenticacion respuesta = servicioAutenticacion.registrar(solicitud);

        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }
    
    @PostMapping("/login")
    public ResponseEntity<RespuestaAutenticacion> login(@Valid @RequestBody SolicitudInicioSesion solicitud){

        return ResponseEntity.ok(servicioAutenticacion.login (solicitud));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RespuestaAutenticacion> refrescar(@Valid @RequestBody SolicitudDeActualizacion solicitud){

        return ResponseEntity.ok(servicioAutenticacion.refrescar(solicitud.getTokenActualizacion()));
    }

    @PostMapping("/logout")
    public ResponseEntity <Void> CerrarSesión(@Valid @RequestBody SolicitudDeActualizacion solicitud){

        servicioAutenticacion.cerrarSesion(solicitud.getTokenActualizacion());

        return ResponseEntity.noContent().build();
    }
}
