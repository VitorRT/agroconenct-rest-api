package br.com.smashcode.api.agroconnect.service.comentario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.smashcode.api.agroconnect.dto.comentario.GetRequestComentario;
import br.com.smashcode.api.agroconnect.model.Comentario;

public interface ComentarioService {
    public Page<GetRequestComentario> findAll(Pageable pageable);
    public Page<GetRequestComentario> findAllByPostagem(String postagem, Pageable pageable);
    public GetRequestComentario save(Comentario comentario);
    public GetRequestComentario findByIdOrElseThrowBadRequestExcepetion(String id);
    public GetRequestComentario updateByIdOrElseThrowBadRequestException(String id, Comentario comentario);
    public void deleteByIdOrElseThrowBadRequestException(String id);
}
