package br.com.smashcode.api.agroconnect.service.postagem;

import java.util.List;

import br.com.smashcode.api.agroconnect.dto.postagem.GetRequestPostagem;
import br.com.smashcode.api.agroconnect.model.Postagem;

public interface PostagemService {

    public List<GetRequestPostagem> findAll();
    public List<GetRequestPostagem> findAllByUsuario(String usuarioId);
    public GetRequestPostagem save(Postagem postagem);
    public GetRequestPostagem findByIdOrElseThrowBadRequestExcepetion(String id);
    public GetRequestPostagem updateByIdOrElseThrowBadRequestException(String id, Postagem postagem);
    public void deleteByIdOrElseThrowBadRequestException(String id);
}
