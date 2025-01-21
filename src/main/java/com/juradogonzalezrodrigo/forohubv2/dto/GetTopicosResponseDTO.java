package com.juradogonzalezrodrigo.forohubv2.dto;

import com.juradogonzalezrodrigo.forohubv2.model.Topico;

public record GetTopicosResponseDTO (
    Integer id,
    String titulo,
    String mensaje,
    String fechaCreacion,
    String autor,
    String curso
)  {
    public GetTopicosResponseDTO (Topico topico){
        this(   topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion().toString(),
                topico.getAutor().getNombre(),
                topico.getCurso()
        );
        }
}
