package me.plurg.articles.exception;

import me.plurg.articles.external.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ArticleException.class)
    public ResponseEntity<ErrorResponse> handleArticleException(ArticleException articleException){
        return new ResponseEntity<>(ErrorResponse.builder()
                .errorMessage(articleException.getMessage())
                .errorCode(articleException.getErrorCode())
                .build(),
                HttpStatus.valueOf(articleException.getStatus()));
    }
}
