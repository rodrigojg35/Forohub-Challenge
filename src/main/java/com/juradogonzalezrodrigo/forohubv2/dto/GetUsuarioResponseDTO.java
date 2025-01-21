package com.juradogonzalezrodrigo.forohubv2.dto;

import com.juradogonzalezrodrigo.forohubv2.model.Usuario;

public record GetUsuarioResponseDTO (
        Integer id,
        String nombre,
        String email
){
    public GetUsuarioResponseDTO (Usuario usuario){
        this(   usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail()
        );
    }
}
