package com.david.ApiMesaAyuda.dto;

import com.david.ApiMesaAyuda.entity.EstadoTicket;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EstadoUpdateRequest {
    
    @NotNull(message = "El estado es obligatorio")
    private EstadoTicket estado;
}
