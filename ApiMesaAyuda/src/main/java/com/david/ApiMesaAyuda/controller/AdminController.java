package com.david.ApiMesaAyuda.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.david.ApiMesaAyuda.dto.SolicitudAscenso;
import com.david.ApiMesaAyuda.dto.UsuarioResponse;
import com.david.ApiMesaAyuda.entity.Usuario;
import com.david.ApiMesaAyuda.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    
    private final UsuarioService usuarioService;

    @PostMapping("/soporte")
    public ResponseEntity<UsuarioResponse> ascenderASoporte(@Valid @RequestBody SolicitudAscenso solicitud){

        Usuario usuario = usuarioService.ascenderASoporte(solicitud.getEmail());

        return ResponseEntity.ok(UsuarioResponse.desde(usuario));
    }
}
