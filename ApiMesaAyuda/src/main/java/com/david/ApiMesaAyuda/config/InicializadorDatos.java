package com.david.ApiMesaAyuda.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.david.ApiMesaAyuda.entity.Rol;
import com.david.ApiMesaAyuda.entity.Usuario;
import com.david.ApiMesaAyuda.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InicializadorDatos implements CommandLineRunner{

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encriptadorContrasenas;

    public void run(String... args){
        
        if (!usuarioRepository.existsByEmail("admin@helpdesk.com")){
            Usuario admin = Usuario.builder()
                .nombre("Administrador")
                .email("admin@helpdesk.com")
                .password(encriptadorContrasenas.encode("admin123"))
                .rol(Rol.ADMIN)
                .build();
            usuarioRepository.save(admin);
        }
    }

}
