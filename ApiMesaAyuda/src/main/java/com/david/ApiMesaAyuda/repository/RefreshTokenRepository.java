package com.david.ApiMesaAyuda.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.david.ApiMesaAyuda.entity.RefreshToken;
import com.david.ApiMesaAyuda.entity.Usuario;

public interface RefreshTokenRepository extends JpaRepository <RefreshToken, Long>{
    
    Optional<RefreshToken> findByToken(String tocken);

    void deleteByUsuario(Usuario usuario);
}
