package com.david.ApiMesaAyuda.dto;

import java.time.LocalDateTime;

import com.david.ApiMesaAyuda.entity.EstadoTicket;
import com.david.ApiMesaAyuda.entity.Prioridad;
import com.david.ApiMesaAyuda.entity.Ticket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class TicketResponse {
    private Long id;
    private String titulo;
    private String descripcion;
    private Prioridad prioridad;
    private EstadoTicket estado;
    private LocalDateTime creadoEn;
    private LocalDateTime slaVenceEn;
    private boolean vencido;
    private String creadoPorEmail;

    public static TicketResponse desde (Ticket ticket){
        return TicketResponse.builder()
            .id(ticket.getId())
            .titulo(ticket.getTitulo())
            .descripcion(ticket.getDescripcion())
            .prioridad(ticket.getPrioridad())
            .estado(ticket.getEstado())
            .creadoEn(ticket.getCreadoEn())
            .slaVenceEn(ticket.getSlaVenceEn())
            .vencido(ticket.isVencido())
            .creadoPorEmail(ticket.getCreadoPor().getEmail())
            .build();
    }
}
