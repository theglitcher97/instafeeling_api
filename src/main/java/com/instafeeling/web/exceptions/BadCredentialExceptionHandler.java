package com.instafeeling.web.exceptions;

import com.instafeeling.web.dtos.GenericErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class BadCredentialExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<GenericErrorResponseDTO> badCredentials(BadCredentialsException e){
        GenericErrorResponseDTO responseDTO = new GenericErrorResponseDTO(
                new Date().toString(),
                HttpStatus.BAD_REQUEST.value(),
                "BAD_CREDENTIALS",
                e.getMessage()
                );
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }
}
