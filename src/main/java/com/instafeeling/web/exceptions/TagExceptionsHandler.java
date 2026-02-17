package com.instafeeling.web.exceptions;

import com.instafeeling.web.dtos.GenericErrorResponse;
import com.instafeeling.web.exceptions.custom.InvalidTagFormatException;
import com.instafeeling.web.exceptions.custom.TagNameTooLongException;
import com.instafeeling.web.exceptions.custom.TooManyTagsOnSingleContentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TagExceptionsHandler {
    @ExceptionHandler(exception = InvalidTagFormatException.class)
    public ResponseEntity<GenericErrorResponse> invalidTagFormat(InvalidTagFormatException e){
        GenericErrorResponse responseDTO = new GenericErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "INVALID_TAG_FORMAT",
                e.getMessage());
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(exception = TagNameTooLongException.class)
    public ResponseEntity<GenericErrorResponse> nameTooLong(TagNameTooLongException e){
        GenericErrorResponse responseDTO = new GenericErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "TAG_NAME_TOO_LONG",
                e.getMessage());
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }
}
