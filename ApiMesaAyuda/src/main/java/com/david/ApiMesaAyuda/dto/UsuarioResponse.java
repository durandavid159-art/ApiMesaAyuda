package com.david.ApiMesaAyuda.dto;

import com.david.ApiMesaAyuda.entity.Rol;
import com.david.ApiMesaAyuda.entity.Usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UsuarioResponse {
    private Long id;
    private String nombre;
    private String email;
    private Rol rol;   
    
    public static UsuarioResponse desde (Usuario usuario){
        return UsuarioResponse.builder()
            .id(usuario.getId())
            .nombre(usuario.getNombre())
            .email(usuario.getEmail())
            .rol(usuario.getRol())
            .build();
    }
}
