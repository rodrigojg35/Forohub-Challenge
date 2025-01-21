package com.juradogonzalezrodrigo.forohubv2.dto;

import com.juradogonzalezrodrigo.forohubv2.model.Topico;

public record GetTopicoResponseDTO (
        Integer id,
        String titulo,
        String mensaje,
        String fechaCreacion,
        GetUsuarioResponseDTO autor,
        String curso
)  {
    public GetTopicoResponseDTO (Topico topico){
        this(   topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion().toString(),
                new GetUsuarioResponseDTO(topico.getAutor()),
                topico.getCurso()
        );
    }
}
