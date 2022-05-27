package com.epam.billing.exeption;

public class DBException extends RuntimeException {

    private static final long serialVersionUID = -2149290244422288198L;

    public DBException() {

        super();
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }


}
