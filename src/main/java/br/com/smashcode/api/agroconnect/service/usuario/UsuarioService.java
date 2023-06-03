package br.com.smashcode.api.agroconnect.service.usuario;

import java.util.List;

import br.com.smashcode.api.agroconnect.model.Usuario;

public interface UsuarioService {
    
    public List<Usuario> findAll();
    public Usuario save(Usuario usuario);
    public Usuario findByIdOrElseThrowBadRequestExcepetion(String id);
    public Usuario updateByIdOrElseThrowBadRequestException(String id, Usuario usuario);
    public void deleteByIdOrElseThrowBadRequestException(String id);
}
