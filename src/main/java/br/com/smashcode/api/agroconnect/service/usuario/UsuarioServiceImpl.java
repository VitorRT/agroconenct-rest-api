package br.com.smashcode.api.agroconnect.service.usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario findByIdOrElseThrowBadRequestExcepetion(String id) {
        return getUsuarioOrElseThrowBadRequestException(id);
    }

    @Override
    public Usuario save(Usuario usuario) {
        usuario.prepararRegistro();
        return usuarioRepository.saveAndFlush(usuario);
    }

    @Override
    public Usuario updateByIdOrElseThrowBadRequestException(String id, Usuario usuario) {
        Usuario found = getUsuarioOrElseThrowBadRequestException(id);
        usuario.prepararAtualizacao(found);
        return usuarioRepository.saveAndFlush(usuario);
    }
    
    private Usuario getUsuarioOrElseThrowBadRequestException(String id) {
        return usuarioRepository.findById(id).orElseThrow(
            () -> new BadRequestException("Nenhum usuário foi encontrado com esse id.")
        );
    }
}
