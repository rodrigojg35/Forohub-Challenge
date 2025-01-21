package com.juradogonzalezrodrigo.forohubv2.service;

import com.juradogonzalezrodrigo.forohubv2.dto.CreateTopicoRequestDTO;
import com.juradogonzalezrodrigo.forohubv2.exception.CustomException;

import com.juradogonzalezrodrigo.forohubv2.dto.GetTopicoResponseDTO;
import com.juradogonzalezrodrigo.forohubv2.dto.GetTopicosResponseDTO;
import com.juradogonzalezrodrigo.forohubv2.dto.PagedResponse;
import com.juradogonzalezrodrigo.forohubv2.model.Topico;
import com.juradogonzalezrodrigo.forohubv2.model.Usuario;
import com.juradogonzalezrodrigo.forohubv2.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.juradogonzalezrodrigo.forohubv2.repository.TopicoRepository;

import java.util.Optional;

import java.time.LocalDate;
import java.util.List;

@Service
public class TopicoService {

    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public TopicoService(TopicoRepository topicoRepository, UsuarioRepository usuarioRepository) {
        this.topicoRepository = topicoRepository;
        this.usuarioRepository = usuarioRepository;
    }



    public PagedResponse<GetTopicosResponseDTO> getAllTopicos(Pageable pageable) {

        Page<Topico> page = topicoRepository.findAll(pageable);

        if (page.isEmpty()) {
            return new PagedResponse<>(List.of(), page.getTotalPages(), page.getTotalElements());
        }

        List<GetTopicosResponseDTO> content = page.map(GetTopicosResponseDTO::new).getContent();
        return new PagedResponse<>(content, page.getTotalPages(), page.getTotalElements());
    }

    public GetTopicoResponseDTO getTopicoById(int id) {

            Topico topico = topicoRepository.findById((long) id).orElseThrow(()
                    -> new CustomException("El tópico con ese id no existe", HttpStatus.NOT_FOUND));

            return new GetTopicoResponseDTO(topico);

    }

    public GetTopicoResponseDTO createTopico(CreateTopicoRequestDTO createTopicoRequestDTO) {

        // Validar que existe el usuario
        Usuario usuario = usuarioRepository.findById((long) createTopicoRequestDTO.getIdAutor()).orElseThrow(()
                -> new CustomException("El usuario con ese id no existe", HttpStatus.NOT_FOUND));


        // Validar que no existan tópicos con mismo titulo y mensaje
        Optional<Topico> topicoIgual =
                topicoRepository.findByTituloAndMensaje(createTopicoRequestDTO.getTitulo(), createTopicoRequestDTO.getMensaje());

        if (topicoIgual.isPresent()) {
            throw new CustomException("Ya existe un tópico con ese título y mensaje", HttpStatus.CONFLICT);
        }


        Topico topico =
                new Topico(
                        createTopicoRequestDTO.getTitulo(),
                        createTopicoRequestDTO.getMensaje(),
                        LocalDate.now(), // fecha de hoy
                        usuario,
                        createTopicoRequestDTO.getCurso()
                );

        Topico generatedTopico = topicoRepository.save(topico);

        return new GetTopicoResponseDTO(generatedTopico);
    }
}