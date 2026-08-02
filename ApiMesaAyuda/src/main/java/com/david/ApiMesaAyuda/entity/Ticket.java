package com.david.ApiMesaAyuda.entity;

import java.time.LocalDateTime;

import jakarta.persistence.PrePersist;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder  
public class Ticket {
    private long id;
    private String titulo;
    private String descripcion;
    private Prioridad prioridad;
    private EstadoTicket estado;
    private LocalDateTime creadoEn;
    private LocalDateTime slaVenceEn;
    private Usuario creadoPor;

    @Transient
    public boolean isVencido() {
        return estado != EstadoTicket.RESUELTO && LocalDateTime.now().isAfter(slaVenceEn);
    }

    @PrePersist
    public void alCrear() {
        if (creadoEn == null) {
            creadoEn = LocalDateTime.now();
        }
        if (slaVenceEn == null && prioridad != null) {
            slaVenceEn = creadoEn.plusHours(prioridad.getHorasSla());
        }
        if (estado == null) {
            estado = EstadoTicket.ABIERTO;
        }
    }
}
