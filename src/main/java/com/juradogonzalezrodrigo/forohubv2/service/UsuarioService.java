package com.juradogonzalezrodrigo.forohubv2.service;

import com.juradogonzalezrodrigo.forohubv2.exception.CustomException;
import com.juradogonzalezrodrigo.forohubv2.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.juradogonzalezrodrigo.forohubv2.repository.UsuarioRepository;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void validarCredenciales(String email, String password) {

        Optional<Usuario> user = usuarioRepository.getUsuariosByEmail(email);

        if(!user.isPresent()) {
            throw new
                    CustomException("El usuario con ese email no existe", HttpStatus.NOT_FOUND);
        }

        if(!user.get().getPassword().equals(password)) {
            throw new
                    CustomException("La contraseña no es correcta", HttpStatus.UNAUTHORIZED);
        }



    }
}