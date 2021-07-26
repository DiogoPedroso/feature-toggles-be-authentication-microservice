package com.pedroso.diogo.assignment.authentication.security;

public class GenericResponseModel {
    private String message;

    public GenericResponseModel(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
