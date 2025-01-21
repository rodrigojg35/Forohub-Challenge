package com.juradogonzalezrodrigo.forohubv2.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class LoginRequestDTO {

    @NotNull(message = "El email es obligatorio")
    @NotEmpty(message = "El email no puede estar vacío")
    private String email;

    @NotNull(message = "La contraseña es obligatoria")
    @NotEmpty(message = "La contraseña no puede estar vacía")
    private String password;

    // Constructor
    public LoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}