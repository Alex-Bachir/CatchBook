package com.catchbook.authservice.dto;

public class RegisterRequest {
    private String email;
    private String password;
    private String pseudo;
    private String FirstName;
    private String LastName;

    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

    public String getPseudo() {
        return pseudo;
    }
    public String getFirstName() {
        return FirstName;
    }
    public String getLastName() {
        return LastName;
    }



}