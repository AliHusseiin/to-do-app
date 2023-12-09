package com.valeo.task.exception;

public class RequestParamMissingException extends RuntimeException {
    public RequestParamMissingException(String paramName) {
        super("Required request parameter '" + paramName + "' is missing");
    }
}
