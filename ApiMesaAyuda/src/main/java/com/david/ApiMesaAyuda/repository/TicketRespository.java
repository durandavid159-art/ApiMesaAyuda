package com.david.ApiMesaAyuda.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.david.ApiMesaAyuda.entity.EstadoTicket;
import com.david.ApiMesaAyuda.entity.Ticket;
import com.david.ApiMesaAyuda.entity.Usuario;

public interface TicketRespository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByCreadoPorOrderByCreadoEnDesc (Usuario creadorPor);
    List<Ticket> findByEstadoNotAndSlaVenceEnBefore(EstadoTicket estado, LocalDateTime fecha);
}
