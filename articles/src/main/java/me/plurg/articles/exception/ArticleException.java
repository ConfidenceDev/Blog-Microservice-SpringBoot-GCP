package me.plurg.articles.exception;

import lombok.Data;

@Data
public class ArticleException extends RuntimeException {

    private String errorCode;
    private int status;

    public ArticleException(String message, String errorCode, int status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }
}
