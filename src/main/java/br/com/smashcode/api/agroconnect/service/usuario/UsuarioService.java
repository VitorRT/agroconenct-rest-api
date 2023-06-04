package br.com.smashcode.api.agroconnect.service.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;

import br.com.smashcode.api.agroconnect.model.Usuario;

public interface UsuarioService {
    
    public Page<Usuario> searchAll(Pageable pageable, String search);
    public EntityModel<Usuario> save(Usuario usuario);
    public EntityModel<Usuario> findByIdOrElseThrowBadRequestExcepetion(String id);
    public EntityModel<Usuario> updateByIdOrElseThrowBadRequestException(String id, Usuario usuario);
    public void deleteByIdOrElseThrowBadRequestException(String id);
}
