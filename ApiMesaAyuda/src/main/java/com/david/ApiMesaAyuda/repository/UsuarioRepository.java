package com.david.ApiMesaAyuda.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.david.ApiMesaAyuda.entity.Usuario;



public interface UsuarioRepository  extends JpaRepository<Usuario, Long>{
    Optional <Usuario> findByEmail(String email);

    boolean existexistsByEmail(String email);
}
