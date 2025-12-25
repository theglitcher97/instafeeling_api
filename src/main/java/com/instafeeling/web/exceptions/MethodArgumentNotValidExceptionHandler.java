package com.instafeeling.web.exceptions;

import com.instafeeling.web.dtos.GenericErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericErrorResponseDTO> invalidMethodArgument(MethodArgumentNotValidException e){
        StringBuilder errorMessages = new StringBuilder();

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errorMessages.append(fieldError.getField())
                    .append(" : ")
                    .append(fieldError.getDefaultMessage())
                    .append(";");
        }

        GenericErrorResponseDTO responseDTO = new GenericErrorResponseDTO(
                new Date().toString(),
                HttpStatus.BAD_REQUEST.value(),
                "BAD_REQUEST",
                errorMessages.toString()
        );
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }
}
