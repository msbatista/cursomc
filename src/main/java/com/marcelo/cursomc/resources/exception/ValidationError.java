package com.marcelo.cursomc.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandartError {
    private static final long serialVersionUID = 1L;
    private final List<FieldExceptionMessage> errors = new ArrayList<FieldExceptionMessage>();

    public ValidationError(Integer status, String message, Long timeStamp) {
        super(status, message, timeStamp);
    }

    public List<FieldExceptionMessage> getErrors() {
        return errors;
    }

    public void addError(String fieldName, String message) {
        errors.add(new FieldExceptionMessage(fieldName, message));
    }

    public void addError(FieldExceptionMessage fieldExceptionMessage) {
        errors.add(fieldExceptionMessage);
    }
}
