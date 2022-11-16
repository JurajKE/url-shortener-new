package com.example.sortener.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RecordFoundException(String message) {
        super(message);
    }
}
