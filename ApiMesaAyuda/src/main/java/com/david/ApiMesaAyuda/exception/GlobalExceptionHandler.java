package com.david.ApiMesaAyuda.exception;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.david.ApiMesaAyuda.dto.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse>manejarAccesoDenegado(AccessDeniedException ex, HttpServletRequest request){
        return construir(HttpStatus.FORBIDDEN, "No tiene permisos para realizar esa acción", request, null);
    }

    private ResponseEntity<ErrorResponse> construir(HttpStatus status, String mensaje, HttpServletRequest request, List<String> detalles){

        ErrorResponse body = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(mensaje)
                .path(request.getRequestURI())
                .detalles(detalles)
                .build();
        return ResponseEntity.status(status).body(body);
    }
}
