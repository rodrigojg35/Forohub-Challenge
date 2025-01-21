package com.juradogonzalezrodrigo.forohubv2.dto;

public record LoginResponseDTO(
        String token,
        Long expiresIn
) {
    public LoginResponseDTO (String token, Long expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }
}
