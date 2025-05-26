package com.tix.nostra.nostra_tix.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DuplicateUserDataException extends RuntimeException {
    public DuplicateUserDataException(String message) {
        super(message);
    }
}