package br.com.smashcode.api.agroconnect.service.comentario;

import java.util.List;

import br.com.smashcode.api.agroconnect.dto.comentario.GetRequestComentario;
import br.com.smashcode.api.agroconnect.model.Comentario;

public interface ComentarioService {
    public List<GetRequestComentario> findAll();
    public List<GetRequestComentario> findAllByPostagem(String postagem);
    public GetRequestComentario save(Comentario comentario);
    public GetRequestComentario findByIdOrElseThrowBadRequestExcepetion(String id);
    public GetRequestComentario updateByIdOrElseThrowBadRequestException(String id, Comentario comentario);
    public void deleteByIdOrElseThrowBadRequestException(String id);
}
