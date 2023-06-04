package br.com.smashcode.api.agroconnect.service.usuario;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import br.com.smashcode.api.agroconnect.exception.dto.BadRequestException;
import br.com.smashcode.api.agroconnect.model.Usuario;
import br.com.smashcode.api.agroconnect.repository.UsuarioRepository;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void deleteByIdOrElseThrowBadRequestException(String id) {
        Usuario usuario = getUsuarioOrElseThrowBadRequestException(id);
        usuarioRepository.delete(usuario);
    }

    @Override
    public Page<Usuario> searchAll(Pageable pageable, String search) {
        if(search != null) {
            return usuarioRepository.findByNomeContaining(pageable,search);
        }
        return usuarioRepository.findAll(pageable);
    }

    @Override
    public EntityModel<Usuario> findByIdOrElseThrowBadRequestExcepetion(String id) {
        return getUsuarioOrElseThrowBadRequestException(id).toEntityModel();
    }

    @Override
    public EntityModel<Usuario> save(Usuario usuario) {
        usuario.prepararRegistro();
        return usuarioRepository.saveAndFlush(usuario).toEntityModel();
    }

    @Override
    public EntityModel<Usuario> updateByIdOrElseThrowBadRequestException(String id, Usuario usuario) {
        Usuario found = getUsuarioOrElseThrowBadRequestException(id);
        usuario.prepararAtualizacao(found);
        return usuarioRepository.saveAndFlush(usuario).toEntityModel();
    }
    
    private Usuario getUsuarioOrElseThrowBadRequestException(String id) {
        return usuarioRepository.findById(id).orElseThrow(
            () -> new BadRequestException("Nenhum usuário foi encontrado com esse id.")
        );
    }
}
