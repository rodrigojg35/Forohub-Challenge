package com.juradogonzalezrodrigo.forohubv2.service;

import com.juradogonzalezrodrigo.forohubv2.dto.*;
import com.juradogonzalezrodrigo.forohubv2.exception.CustomException;

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

    public GetTopicoResponseDTO updateTopico(int id, UpdateTopicoRequestDTO updateTopicoRequestDTO) {

        // Validar que existe el topico a editar
        Topico topico = topicoRepository.findById((long) id).
                orElseThrow(() -> new CustomException("El tópico con ese id no existe", HttpStatus.NOT_FOUND));

        // Validar que se envió almenos un atributo a editar
        if (updateTopicoRequestDTO.getTitulo() == null
                && updateTopicoRequestDTO.getMensaje() == null
                && updateTopicoRequestDTO.getIdAutor() == null
                && updateTopicoRequestDTO.getCurso() == null) {
            throw new CustomException("Debe enviar al menos un campo a editar", HttpStatus.BAD_REQUEST);
        }

        if( updateTopicoRequestDTO.getIdAutor() != null){
            // Validar que existe el usuario (en caso que si, se añade)
            Usuario user = usuarioRepository.findById((long) updateTopicoRequestDTO.getIdAutor()).orElseThrow(()
                    -> new CustomException("El usuario con ese id no existe", HttpStatus.NOT_FOUND));

            topico.setAutor(user);
        }

        // Si se quiere cambiar ya sea el titulo o mensaje,
        // se checa que no terminen siendo iguales a otro tópico
        if (updateTopicoRequestDTO.getTitulo() != null || updateTopicoRequestDTO.getMensaje() != null) {

            // Si solo se mandó uno, se mantiene el otro igual para la comparacion
            if( updateTopicoRequestDTO.getTitulo() == null){
                updateTopicoRequestDTO.setTitulo(topico.getTitulo());
            }

            if( updateTopicoRequestDTO.getMensaje() == null){
                updateTopicoRequestDTO.setMensaje(topico.getMensaje());
            }

            // Validar que no existan tópicos con mismo titulo y mensaje
            Optional<Topico> topicoIgual =
                    topicoRepository.findByTituloAndMensaje(updateTopicoRequestDTO.getTitulo(), updateTopicoRequestDTO.getMensaje());

            if (topicoIgual.isPresent()) {
                throw new CustomException("Ya existe un tópico con ese título y mensaje", HttpStatus.CONFLICT);
            }

            // Si no se repite, se procede a editar
            topico.setTitulo(updateTopicoRequestDTO.getTitulo());
            topico.setMensaje(updateTopicoRequestDTO.getMensaje());
        }

        if (updateTopicoRequestDTO.getCurso() != null){
            topico.setCurso(updateTopicoRequestDTO.getCurso());
        }

        // Guardar cambios
        Topico updatedTopico = topicoRepository.save(topico);

        // Devolver el tópico actualizado
        return new GetTopicoResponseDTO(updatedTopico);


    }


    public void deleteTopico(int id) {

        Topico topico = topicoRepository.findById((long) id).orElseThrow(()
                -> new CustomException("El tópico con ese id no existe", HttpStatus.NOT_FOUND));
        topicoRepository.deleteById((long) topico.getId());

    }


}