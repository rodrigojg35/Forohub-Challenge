package com.juradogonzalezrodrigo.forohubv2.controller;


import com.juradogonzalezrodrigo.forohubv2.model.Usuario;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;

import org.springframework.web.bind.annotation.*;

import com.juradogonzalezrodrigo.forohubv2.dto.*;
import com.juradogonzalezrodrigo.forohubv2.service.*;

import org.springframework.http.HttpStatus;

import java.net.URI;
import java.util.Collections;

@RestController
public class MainController {

    private final UsuarioService usuarioService;
    private final TopicoService topicoService;
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    public MainController(UsuarioService usuarioService, TopicoService topicoService, AuthenticationService authenticationService, JwtService jwtService) {
        this.usuarioService = usuarioService;
        this.topicoService = topicoService;
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
    }


    @PostMapping("/auth")
    public ResponseEntity<LoginResponseDTO> authenticate(@Valid @RequestBody LoginRequestDTO loginUserDto) {

        Usuario authenticatedUser = authenticationService.authenticate(loginUserDto);
        System.out.println("Usuario autenticado, generando token");
        String jwtToken = jwtService.generateToken(User.builder()
                .username(authenticatedUser.getEmail())
                .password(authenticatedUser.getPassword())
                .authorities(Collections.emptyList())
                .build());

        LoginResponseDTO loginResponse = new LoginResponseDTO(
                jwtToken,
                jwtService.getExpirationTime()
        );

        return ResponseEntity.ok(loginResponse);
    }


    @GetMapping("/topicos")
    public PagedResponse<GetTopicosResponseDTO> getAllTopicos(Pageable pageable) {
        return topicoService.getAllTopicos(pageable);
    }

    @GetMapping("/topicos/{id}")
    public GetTopicoResponseDTO getTopicoById(@PathVariable int id) {
        return topicoService.getTopicoById(id);
    }

    @Transactional
    @PostMapping("/topicos")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GetTopicoResponseDTO> createTopico(@Valid @RequestBody CreateTopicoRequestDTO createTopicoRequestDTO) {
        // Aqui no hizo falta hacer un DTO para response, ya que devolverá status 201
        // y el objeto del tópico creado reciclando el DTO de petición  y la URI en la cabecera

        var topico = topicoService.createTopico(createTopicoRequestDTO);

        return ResponseEntity.created(URI.create("/topicos/" + topico.id())).body(topico);

    }

    @Transactional
    @PutMapping("/topicos/{id}")
    public ResponseEntity<GetTopicoResponseDTO> updateTopico(@PathVariable int id, @Valid @RequestBody UpdateTopicoRequestDTO updateTopicoRequestDTO) {
        // Igualmente como en el post, misma respuesta con el objeto actualizado con status 200
        GetTopicoResponseDTO updatedTopico = topicoService.updateTopico(id, updateTopicoRequestDTO);
        return ResponseEntity.ok(updatedTopico);
    }

    @Transactional
    @DeleteMapping("/topicos/{id}")
    public ResponseEntity<Void> deleteTopico(@PathVariable int id) {
        topicoService.deleteTopico(id);
        return ResponseEntity.noContent().build();
    }




}
