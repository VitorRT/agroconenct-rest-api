package br.com.smashcode.api.agroconnect.service.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.smashcode.api.agroconnect.dto.usuario.GetRequestUsuario;
import br.com.smashcode.api.agroconnect.model.Usuario;

public interface UsuarioService {
    
    public Page<GetRequestUsuario> searchAll(Pageable pageable, String search);
    public GetRequestUsuario save(Usuario usuario);
    public GetRequestUsuario findByIdOrElseThrowBadRequestExcepetion(String id);
    public GetRequestUsuario updateByIdOrElseThrowBadRequestException(String id, Usuario usuario);
    public void deleteByIdOrElseThrowBadRequestException(String id);
}
