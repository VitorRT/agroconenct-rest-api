package br.com.smashcode.api.agroconnect.service.postagem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.smashcode.api.agroconnect.dto.postagem.GetRequestPostagem;
import br.com.smashcode.api.agroconnect.model.Postagem;

public interface PostagemService {

    public Page<GetRequestPostagem> searchAll(Pageable pageable, String search);
    public Page<GetRequestPostagem> findAllByUsuario(String usuarioId, Pageable pageable);
    public GetRequestPostagem save(Postagem postagem);
    public GetRequestPostagem findByIdOrElseThrowBadRequestExcepetion(String id);
    public GetRequestPostagem updateByIdOrElseThrowBadRequestException(String id, Postagem postagem);
    public void deleteByIdOrElseThrowBadRequestException(String id);
}
