package com.epam.billing.exeption;

public class AppException extends Exception{
    private static final long serialVersionUID = -6206471460901689130L;

    public AppException() {
        super();
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(String message) {
        super(message);
    }
}
