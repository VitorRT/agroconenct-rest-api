package br.com.smashcode.api.agroconnect.service.postagem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.smashcode.api.agroconnect.dto.postagem.GetRequestPostagem;
import br.com.smashcode.api.agroconnect.exception.dto.BadRequestException;
import br.com.smashcode.api.agroconnect.model.Postagem;
import br.com.smashcode.api.agroconnect.model.Usuario;
import br.com.smashcode.api.agroconnect.repository.PostagemRepository;
import br.com.smashcode.api.agroconnect.repository.UsuarioRepository;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class PostagemServiceImpl implements PostagemService {

    @Autowired
    private PostagemRepository postagemRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public void deleteByIdOrElseThrowBadRequestException(String id) {
        Postagem postagem = getPostagemOrElseThrowBadRequestException(id);
        postagemRepository.delete(postagem);
    }

    @Override
    public Page<GetRequestPostagem> searchAll(Pageable pageable, String search) {
        if(search != null) {
            return postagemRepository.findByTituloContaining(pageable, search).map(GetRequestPostagem::new);
        }
        return postagemRepository.findAll(pageable).map(GetRequestPostagem::new);
    }

    @Override
    public Page<GetRequestPostagem> findAllByUsuario(String usuarioId, Pageable pageable) {
        Usuario usuario = getUsuarioOrElseThrowBadRequestException(usuarioId);
        return postagemRepository.findAllByUsuario(usuario, pageable).map(GetRequestPostagem::new);
    }

    @Override
    public GetRequestPostagem findByIdOrElseThrowBadRequestExcepetion(String id) {
        Postagem postagem = getPostagemOrElseThrowBadRequestException(id);
        return toGetRequestPostagem(postagem);
    }

    @Override
    public GetRequestPostagem save(Postagem postagem) {
        postagem.prepararRegistro();
        Usuario usuario = getUsuarioOrElseThrowBadRequestException(postagem.getUsuario().getId());
        postagem.setUsuario(usuario);
        Postagem created = postagemRepository.saveAndFlush(postagem);
        return toGetRequestPostagem(created);
    }

    @Override
    public GetRequestPostagem updateByIdOrElseThrowBadRequestException(String id, Postagem postagem) {
        Postagem found = getPostagemOrElseThrowBadRequestException(id);
        postagem.prepararAtualizacao(found);
        Postagem updated = postagemRepository.saveAndFlush(postagem);
        return toGetRequestPostagem(updated);
    }

    private GetRequestPostagem toGetRequestPostagem(Postagem postagem) {
        return new GetRequestPostagem(postagem);
    }

    private Postagem getPostagemOrElseThrowBadRequestException(String id) {
        return postagemRepository.findById(id).orElseThrow(
            () -> new BadRequestException("Nenhuma postagem foi encontrada com esse id.")
        );
    }

    private Usuario getUsuarioOrElseThrowBadRequestException(String id) {
        return usuarioRepository.findById(id).orElseThrow(
            () -> new BadRequestException("Nenhum usuário foi encontrado com esse id.")
        );
    }
    
}
