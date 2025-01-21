package com.juradogonzalezrodrigo.forohubv2.dto;

import java.util.List;

public class PagedResponse<T> {
    private List<T> contenido;
    private int totalDePaginas;
    private long totalDeTopicos;

    public PagedResponse(List<T> contenido, int totalDePaginas, long totalDeTopicos) {
        this.contenido = contenido;
        this.totalDePaginas = totalDePaginas;
        this.totalDeTopicos = totalDeTopicos;
    }

    public List<T> getContenido() {
        return contenido;
    }

    public int getTotalDePaginas() {
        return totalDePaginas;
    }

    public long getTotalDeTopicos() {
        return totalDeTopicos;
    }
}