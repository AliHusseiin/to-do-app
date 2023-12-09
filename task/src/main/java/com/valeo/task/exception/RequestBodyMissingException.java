package com.valeo.task.exception;

public class RequestBodyMissingException extends RuntimeException {
    public RequestBodyMissingException(String param) {
        super("Request body '" + param + "' is missing");
    }
}

