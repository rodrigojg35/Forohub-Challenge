package com.juradogonzalezrodrigo.forohubv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.juradogonzalezrodrigo.forohubv2.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> getUsuariosByEmail(String email);
}