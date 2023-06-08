package br.com.smashcode.api.agroconnect.service.usuario;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.smashcode.api.agroconnect.dto.usuario.GetRequestUsuario;
import br.com.smashcode.api.agroconnect.dto.usuario.PutRequestUsuario;
import br.com.smashcode.api.agroconnect.exception.dto.BadRequestException;
import br.com.smashcode.api.agroconnect.model.Usuario;
import br.com.smashcode.api.agroconnect.repository.UsuarioRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@NoArgsConstructor
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void deleteByIdOrElseThrowBadRequestException(String id) {
        Usuario usuario = getUsuarioOrElseThrowBadRequestException(id);
        log.info("[ Delete ] - Usuário(a) \""+usuario.getNome()+"\" deletado(a) com sucesso!");
        usuarioRepository.delete(usuario);
    }

    @Override
    public Page<GetRequestUsuario> searchAll(Pageable pageable, String search) {
        log.info("[ Search ] - Listagem geral de todos(a) os usuários(a).");
        if(search != null) {
            return usuarioRepository.findByNomeContaining(pageable,search).map(GetRequestUsuario::new);
        }
        return usuarioRepository.findAll(pageable).map(GetRequestUsuario::new);
    }

    @Override
    public GetRequestUsuario findByIdOrElseThrowBadRequestExcepetion(String id) {
        Usuario usuario = getUsuarioOrElseThrowBadRequestException(id);
        log.info("[ Show ] - Usuário(a) \""+usuario.getNome()+"\" detalhado(a) com sucesso!");
        return toGetRequestUsuario(usuario);
    }

    @Override
    public GetRequestUsuario save(Usuario usuario) {
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        usuario.prepararRegistro();
        Usuario created = usuarioRepository.saveAndFlush(usuario);
        log.info("[ Create ] - Usuário(a) \""+created.getNome()+"\" cadastrado(a) com sucesso!");
        return toGetRequestUsuario(created);
    }

    @Override
    public GetRequestUsuario updateByIdOrElseThrowBadRequestException(String id, PutRequestUsuario usuarioDTO) {
        Usuario usuario = getUsuarioOrElseThrowBadRequestException(id);
        usuario.setNome(usuarioDTO.nome());
        usuario.setEmail(usuarioDTO.email());
        usuario.prepararAtualizacao(usuario);
        Usuario updated = usuarioRepository.saveAndFlush(usuario);
        log.info("[ Update ] - Usuário(a) \""+usuario.getNome()+"\" editado(a) com sucesso!");
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
