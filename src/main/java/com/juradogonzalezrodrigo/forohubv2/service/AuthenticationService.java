package com.juradogonzalezrodrigo.forohubv2.service;


import com.juradogonzalezrodrigo.forohubv2.dto.LoginRequestDTO;
import com.juradogonzalezrodrigo.forohubv2.exception.CustomException;
import com.juradogonzalezrodrigo.forohubv2.model.Usuario;
import com.juradogonzalezrodrigo.forohubv2.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;


    public AuthenticationService(
            UsuarioRepository userRepository,
            AuthenticationManager authenticationManager
    ) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = userRepository;
    }

    public Usuario authenticate(LoginRequestDTO datosLogin) {

        Optional<Usuario> user = usuarioRepository.getUsuariosByEmail(datosLogin.getEmail());

        if(!user.isPresent()) {
            throw new
                    CustomException("El usuario con ese email no existe", HttpStatus.NOT_FOUND);
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        datosLogin.getEmail(),
                        datosLogin.getPassword()
                )
        );

        return user.get();
    }
}