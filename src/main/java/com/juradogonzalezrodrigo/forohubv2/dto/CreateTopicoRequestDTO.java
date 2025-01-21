package com.juradogonzalezrodrigo.forohubv2.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CreateTopicoRequestDTO {

        @NotNull(message = "El titulo es obligatorio")
        @NotEmpty(message = "El titulo no puede estar vacío")
        private String titulo;

        @NotNull(message = "El mensaje es obligatorio")
        @NotEmpty(message = "El mensaje no debería estar vacío")
        private String mensaje;

        @NotNull(message = "El id del autor es obligatorio")
        @JsonAlias("autor")
        private Integer idAutor;

        @NotNull(message = "El nombre del curso es requerido")
        @NotEmpty(message = "El nombre del curso no debería estar vacío")
        private String curso;


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Integer getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Integer idAutor) {
        this.idAutor = idAutor;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
}
