package br.com.smashcode.api.agroconnect.service.comentario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.smashcode.api.agroconnect.dto.comentario.GetRequestComentario;
import br.com.smashcode.api.agroconnect.exception.dto.BadRequestException;
import br.com.smashcode.api.agroconnect.model.Comentario;
import br.com.smashcode.api.agroconnect.model.Postagem;
import br.com.smashcode.api.agroconnect.model.Usuario;
import br.com.smashcode.api.agroconnect.repository.ComentarioRepository;
import br.com.smashcode.api.agroconnect.repository.PostagemRepository;
import br.com.smashcode.api.agroconnect.repository.UsuarioRepository;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class ComentarioServiceImpl implements ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PostagemRepository postagemRepository;

    @Override
    public void deleteByIdOrElseThrowBadRequestException(String id) {
        Comentario comentario = getComentarioOrElseThrowBadRequestException(id);
        comentarioRepository.delete(comentario);
    }

    @Override
    public List<GetRequestComentario> findAll() {
        List<Comentario> comentarios = comentarioRepository.findAll();
        return comentarios.stream().map(GetRequestComentario::new).toList();
    }

    @Override
    public List<GetRequestComentario> findAllByPostagem(String postagemId) {
        Postagem postagem = getPostagemOrElseThrowBadRequestException(postagemId);
        List<Comentario> comentarios = comentarioRepository.findAllByPostagem(postagem);
        return comentarios.stream().map(GetRequestComentario::new).toList();
    }

    @Override
    public GetRequestComentario findByIdOrElseThrowBadRequestExcepetion(String id) {
        Comentario comentario = getComentarioOrElseThrowBadRequestException(id);
        return toGetRequestComentario(comentario);
    }

    @Override
    public GetRequestComentario save(Comentario comentario) {
        Usuario usuario = getUsuarioOrElseThrowBadRequestException(comentario.getUsuario().getId());
        Postagem postagem = getPostagemOrElseThrowBadRequestException(comentario.getPostagem().getId());
        comentario.setUsuario(usuario);
        comentario.setPostagem(postagem);
        comentario.prepararRegistro();
        Comentario created = comentarioRepository.saveAndFlush(comentario);
        return toGetRequestComentario(created);
    }

    @Override
    public GetRequestComentario updateByIdOrElseThrowBadRequestException(String id, Comentario comentario) {
        Comentario found = getComentarioOrElseThrowBadRequestException(id);
        comentario.prepararAtualizacao(found);
        Comentario updated = comentarioRepository.saveAndFlush(comentario);
        return toGetRequestComentario(updated);
    }


    private Comentario getComentarioOrElseThrowBadRequestException(String id) {
        return comentarioRepository.findById(id).orElseThrow(
            () -> new BadRequestException("Nenhum comentário foi encontrado com esse id.")
        );
    }

    private Usuario getUsuarioOrElseThrowBadRequestException(String id) {
        return usuarioRepository.findById(id).orElseThrow(
            () -> new BadRequestException("Nenhum usuario foi encontrado com esse id.")
        );
    }

    private Postagem getPostagemOrElseThrowBadRequestException(String id) {
        return postagemRepository.findById(id).orElseThrow(
            () -> new BadRequestException("Nenhuma postagem foi encontrada com esse id.")
        );
    }

    private GetRequestComentario toGetRequestComentario(Comentario comentario) {
        return new GetRequestComentario(comentario);
    }
    
}
