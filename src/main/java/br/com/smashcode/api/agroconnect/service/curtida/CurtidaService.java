package br.com.smashcode.api.agroconnect.service.curtida;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.smashcode.api.agroconnect.dto.curtida.GetRequestCurtida;
import br.com.smashcode.api.agroconnect.model.Postagem;
import br.com.smashcode.api.agroconnect.model.Usuario;

public interface CurtidaService {
    
    public Page<GetRequestCurtida> findAll(Pageable pageable);
    public Page<GetRequestCurtida> findAllByPostagemOrElseThrowBadRequestException(String postagemId, Pageable pageable);
    public GetRequestCurtida likePost(Usuario usuario, Postagem postagem);    
    public void deslikePostByIdOrElseThrowBadRequestException(String id);

}
