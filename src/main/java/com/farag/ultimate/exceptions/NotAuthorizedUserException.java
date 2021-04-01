package com.farag.ultimate.exceptions;


public class NotAuthorizedUserException extends Exception {
    private static final long serialVersionUID = 1L;

    private String errorMessage;

    public NotAuthorizedUserException() {
        super();
    }

    public NotAuthorizedUserException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
