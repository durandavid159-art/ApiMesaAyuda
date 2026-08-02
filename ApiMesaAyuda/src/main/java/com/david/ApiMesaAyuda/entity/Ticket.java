package com.david.ApiMesaAyuda.entity;

import java.time.LocalDateTime;

public class Ticket {
    private long id;
    private String titulo;
    private String descripcion;
    private Prioridad prioridad;
    private EstadoTicket estado;
    private LocalDateTime creadaEn;
    private LocalDateTime slaVenceEn;
    private Usuario creadoPor;
}
