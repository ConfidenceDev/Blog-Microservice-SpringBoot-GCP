package me.plurg.creator.exception;

import lombok.Data;

@Data
public class CreatorException extends RuntimeException{

    private String errorCode;
    private int status;

    public CreatorException(String message, String errorCode, int status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }
}
