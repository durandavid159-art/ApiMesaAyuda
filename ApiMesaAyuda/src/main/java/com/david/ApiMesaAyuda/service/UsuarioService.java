package com.david.ApiMesaAyuda.service;

import org.springframework.stereotype.Service;

import com.david.ApiMesaAyuda.entity.Rol;
import com.david.ApiMesaAyuda.entity.Usuario;
import com.david.ApiMesaAyuda.exception.RecursoNoEncontradoException;
import com.david.ApiMesaAyuda.repository.UsuarioRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;

    private Usuario buscarPorEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado: " + email));
    }

    @Transactional
    public Usuario ascenderaASoporte(String email){
        Usuario usuario = buscarPorEmail(email);
        usuario.setRol(Rol.SOPORTE);
        return usuarioRepository.save(usuario);
    }
}
