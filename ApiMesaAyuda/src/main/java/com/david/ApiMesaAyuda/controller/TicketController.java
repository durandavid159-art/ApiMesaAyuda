package com.david.ApiMesaAyuda.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.david.ApiMesaAyuda.dto.EstadoUpdateRequest;
import com.david.ApiMesaAyuda.dto.TicketRequest;
import com.david.ApiMesaAyuda.dto.TicketResponse;
import com.david.ApiMesaAyuda.entity.Ticket;
import com.david.ApiMesaAyuda.entity.Usuario;
import com.david.ApiMesaAyuda.service.TicketService;
import com.david.ApiMesaAyuda.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
    
    private final TicketService ticketService;
    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<TicketResponse> crear(@Valid @RequestBody TicketRequest solicitud, Authentication authentication){
        Usuario usuario = usuarioActual(authentication);
        Ticket ticket = ticketService.crear(solicitud, usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(TicketResponse.desde(ticket));
    }

    @GetMapping("/mios")
    public ResponseEntity<List<TicketResponse>> misTickets(Authentication authentication){
        Usuario usuario = usuarioActual(authentication);

        List<TicketResponse> respuesta = ticketService.misTikets(usuario).stream().map(TicketResponse :: desde).toList();

        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> obtenerPorId(@PathVariable Long id, Authentication authentication){

        Usuario usuario = usuarioActual(authentication);
        Ticket ticket = ticketService.obtenerPorId(id, usuario);

        return ResponseEntity.ok(TicketResponse.desde(ticket));
    }

    @GetMapping
    public ResponseEntity<List<TicketResponse>> listarTodos(){

        List<TicketResponse> respuesta = ticketService.listarTodos().stream().map(TicketResponse::desde).toList();

        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/vencidos")
    public ResponseEntity<List<TicketResponse>> vencidos(){
        List<TicketResponse> respuesta = ticketService.vencidos().stream().map(TicketResponse::desde).toList();

        return ResponseEntity.ok(respuesta);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity <TicketResponse> actualizarEstado(@PathVariable Long id, @Valid @RequestBody EstadoUpdateRequest solicitud){

        Ticket ticket = ticketService.actulizarEstado(id, solicitud.getEstado());

        return ResponseEntity.ok(TicketResponse.desde(ticket));
    }

    private Usuario usuarioActual(Authentication authentication) {
        
        return usuarioService.buscarPorEmail(authentication.getName());
    }
}
