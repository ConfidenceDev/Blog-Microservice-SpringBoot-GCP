package me.plurg.creator.exception;

import me.plurg.creator.external.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CreatorException.class)
    public ResponseEntity<ErrorResponse> handleCreatorException(CreatorException creatorException){
        return new ResponseEntity<>(ErrorResponse.builder()
                .errorMessage(creatorException.getMessage())
                .errorCode(creatorException.getErrorCode())
                .build(),
                HttpStatus.valueOf(creatorException.getStatus()));
    }
}
