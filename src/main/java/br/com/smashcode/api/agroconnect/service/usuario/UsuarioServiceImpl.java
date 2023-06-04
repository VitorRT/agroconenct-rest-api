package br.com.smashcode.api.agroconnect.service.usuario;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.smashcode.api.agroconnect.dto.usuario.GetRequestUsuario;
import br.com.smashcode.api.agroconnect.exception.dto.BadRequestException;
import br.com.smashcode.api.agroconnect.model.Usuario;
import br.com.smashcode.api.agroconnect.repository.UsuarioRepository;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void deleteByIdOrElseThrowBadRequestException(String id) {
        Usuario usuario = getUsuarioOrElseThrowBadRequestException(id);
        usuarioRepository.delete(usuario);
    }

    @Override
    public Page<GetRequestUsuario> searchAll(Pageable pageable, String search) {
        if(search != null) {
            return usuarioRepository.findByNomeContaining(pageable,search).map(GetRequestUsuario::new);
        }
        return usuarioRepository.findAll(pageable).map(GetRequestUsuario::new);
    }

    @Override
    public GetRequestUsuario findByIdOrElseThrowBadRequestExcepetion(String id) {
        Usuario usuario = getUsuarioOrElseThrowBadRequestException(id);
        return toGetRequestUsuario(usuario);
    }

    @Override
    public GetRequestUsuario save(Usuario usuario) {
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        usuario.prepararRegistro();
        Usuario created = usuarioRepository.saveAndFlush(usuario);
        return toGetRequestUsuario(created);
    }

    @Override
    public GetRequestUsuario updateByIdOrElseThrowBadRequestException(String id, Usuario usuario) {
        Usuario found = getUsuarioOrElseThrowBadRequestException(id);
        usuario.prepararAtualizacao(found);
        Usuario updated = usuarioRepository.saveAndFlush(usuario);
        return toGetRequestUsuario(updated);
    }
    
    private Usuario getUsuarioOrElseThrowBadRequestException(String id) {
        return usuarioRepository.findById(id).orElseThrow(
            () -> new BadRequestException("Nenhum usuário foi encontrado com esse id.")
        );
    }
    private GetRequestUsuario toGetRequestUsuario(Usuario usuario) {
        return new GetRequestUsuario(usuario);
    }
}
