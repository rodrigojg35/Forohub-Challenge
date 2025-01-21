package com.juradogonzalezrodrigo.forohubv2.dto;

public class UpdateTopicoRequestDTO {

    private String titulo;
    private String mensaje;
    private Integer idAutor;
    private String curso;

    public UpdateTopicoRequestDTO() {
    }

    public UpdateTopicoRequestDTO(String titulo, String mensaje, Integer idAutor, String curso) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.idAutor = idAutor;
        this.curso = curso;
    }

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
