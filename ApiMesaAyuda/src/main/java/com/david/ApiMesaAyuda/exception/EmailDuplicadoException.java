package com.david.ApiMesaAyuda.exception;

public class EmailDuplicadoException extends RuntimeException{
    
    public EmailDuplicadoException (String email){

        super("Ya existe un usuario regitrado con el email: " + email);
    }

}
