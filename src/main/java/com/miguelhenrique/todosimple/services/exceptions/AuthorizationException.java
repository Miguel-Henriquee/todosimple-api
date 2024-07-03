package com.miguelhenrique.todosimple.services.exceptions;

import org.springframework.security.access.AccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class AuthorizationException extends AccessDeniedException{
    
    public AuthorizationException(String message) {
        super(message);
    }

}
