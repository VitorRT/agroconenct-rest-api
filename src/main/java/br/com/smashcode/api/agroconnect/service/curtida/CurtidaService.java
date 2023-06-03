package br.com.smashcode.api.agroconnect.service.curtida;

import java.util.List;

import br.com.smashcode.api.agroconnect.dto.curtida.GetRequestCurtida;
import br.com.smashcode.api.agroconnect.model.Postagem;
import br.com.smashcode.api.agroconnect.model.Usuario;

public interface CurtidaService {
    
    public List<GetRequestCurtida> findAll();
    public List<GetRequestCurtida> findAllByPostagemOrElseThrowBadRequestException(String postagemId);
    public GetRequestCurtida likePost(Usuario usuario, Postagem postagem);    
    public void deslikePostByIdOrElseThrowBadRequestException(String id);

}
