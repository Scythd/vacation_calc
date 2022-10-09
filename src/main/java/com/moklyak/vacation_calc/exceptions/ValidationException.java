package com.moklyak.vacation_calc.exceptions;

public class ValidationException extends Exception {

    public ValidationException(String s) {
        super(s);
    }

    public ValidationException(IllegalArgumentException e) {
        super(e);
    }

    public ValidationException() {
        super();
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

}
