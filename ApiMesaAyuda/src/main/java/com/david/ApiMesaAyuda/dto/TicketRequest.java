package com.david.ApiMesaAyuda.dto;

import com.david.ApiMesaAyuda.entity.Prioridad;

import lombok.Data;

@Data
public class TicketRequest {
    private String titulo;
    private String descripcion;
    private Prioridad prioridad;
}
