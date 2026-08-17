package com.david.ApiMesaAyuda.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.david.ApiMesaAyuda.dto.TicketRequest;
import com.david.ApiMesaAyuda.entity.EstadoTicket;
import com.david.ApiMesaAyuda.entity.Rol;
import com.david.ApiMesaAyuda.entity.Ticket;
import com.david.ApiMesaAyuda.entity.Usuario;
import com.david.ApiMesaAyuda.exception.RecursoNoEncontradoException;
import com.david.ApiMesaAyuda.repository.TicketRespository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService {
    
    private final TicketRespository ticketRespository;

    @Transactional
    public Ticket crear (TicketRequest request, Usuario creador){

        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime slaVenceEn = ahora.plusHours(request.getPrioridad().getHorasSla());

        Ticket ticket = Ticket.builder()
            .titulo(request.getTitulo())
            .descripcion(request.getDescripcion())
            .prioridad(request.getPrioridad())
            .estado(EstadoTicket.ABIERTO)
            .creadoEn(ahora)
            .slaVenceEn(slaVenceEn)
            .creadoPor(creador)
            .build();
        return ticketRespository.save(ticket);
    }

    public List <Ticket> misTikets(Usuario usuario){
        return ticketRespository.findByCreadoPorOrderByCreadoEnDesc(usuario);
    }

    public List <Ticket> listarTodos(){
        return ticketRespository.findAll();
    }

    public List <Ticket> vencidos(){
        return ticketRespository.findByEstadoNotAndSlaVenceEnBefore(EstadoTicket.RESUELTO, LocalDateTime.now());
    }

    public Ticket obtenerPorId(Long id, Usuario usuarioActual){
        
        Ticket ticket = buscar(id);

        boolean esDueno = ticket.getCreadoPor().getId().equals(usuarioActual.getId());
        boolean esPersonalSoporte = usuarioActual.getRol()== Rol.SOPORTE || usuarioActual.getRol()== Rol.ADMIN;

        if (!esDueno && !esPersonalSoporte){
            throw new AccessDeniedException("No puede consultar un ticket que no le pertenece.");
        }

        return ticket;
    }

    @Transactional
    public Ticket actulizarEstado(Long id, EstadoTicket nuevoEstado){
        Ticket ticket = buscar(id);
        ticket.setEstado(nuevoEstado);
        return ticketRespository.save(ticket);
    }

    private Ticket buscar (Long id){
        return ticketRespository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Ticket no encontrado con id: " + id));
    }
}