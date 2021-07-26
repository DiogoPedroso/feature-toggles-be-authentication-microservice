package com.pedroso.diogo.assignment.authentication.security;

/**
 * 
 * Response Model for Authentication
 * 
 */

public class AuthenticationResponseModel {
    private String jwt;

    public AuthenticationResponseModel(String jwt) {
        this.jwt = jwt;
    }

    public AuthenticationResponseModel() {}

    public String getToken() {
        return this.jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
