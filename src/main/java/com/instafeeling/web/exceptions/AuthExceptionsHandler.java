package com.instafeeling.web.exceptions;

import com.instafeeling.web.dtos.GenericErrorResponse;
import com.instafeeling.web.exceptions.custom.EmailAlreadyTakenException;
import com.instafeeling.web.exceptions.custom.PasswordsDoNotMatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthExceptionsHandler {
    @ExceptionHandler(exception = PasswordsDoNotMatchException.class)
    public ResponseEntity<GenericErrorResponse> passwordsDoNotMatch(PasswordsDoNotMatchException e){
        GenericErrorResponse response = new GenericErrorResponse(HttpStatus.BAD_REQUEST.value(), "PASSWORDS_DO_NOT_MATCH", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(exception = EmailAlreadyTakenException.class)
    public ResponseEntity<GenericErrorResponse> emailAlreadyTaken(EmailAlreadyTakenException e){
        GenericErrorResponse response = new GenericErrorResponse(HttpStatus.BAD_REQUEST.value(), "EMAIL_ALREADY_TAKEN", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
