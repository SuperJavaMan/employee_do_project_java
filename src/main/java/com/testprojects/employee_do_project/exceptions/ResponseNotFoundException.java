package com.testprojects.employee_do_project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResponseNotFoundException extends RuntimeException {

    public ResponseNotFoundException() {
    }

    public ResponseNotFoundException(String message) {
        super(message);
    }

    public ResponseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
