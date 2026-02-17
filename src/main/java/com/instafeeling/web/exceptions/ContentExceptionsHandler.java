package com.instafeeling.web.exceptions;

import com.instafeeling.web.dtos.GenericErrorResponse;
import com.instafeeling.web.exceptions.custom.TooManyTagsOnSingleContentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ContentExceptionsHandler {
    @ExceptionHandler(exception = TooManyTagsOnSingleContentException.class)
    public ResponseEntity<GenericErrorResponse> tooManyTagsONSingleContent(TooManyTagsOnSingleContentException e){
        GenericErrorResponse responseDTO = new GenericErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "TOO_MANY_TAGS_ON_SINGLE_CONTENT",
                e.getMessage());
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }
}
