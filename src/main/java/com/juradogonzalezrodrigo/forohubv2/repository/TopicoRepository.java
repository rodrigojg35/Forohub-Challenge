package com.juradogonzalezrodrigo.forohubv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.juradogonzalezrodrigo.forohubv2.model.Topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    Page<Topico> findAll(Pageable pageable);

    Optional<Topico> findByTituloAndMensaje(String titulo, String mensaje);

}