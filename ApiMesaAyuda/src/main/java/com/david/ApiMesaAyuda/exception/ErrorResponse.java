package com.david.ApiMesaAyuda.exception;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;

@Builder
public class ErrorResponse {
     private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private List<String> detalles;
    
}
