package br.com.smashcode.api.agroconnect.service.curtida;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.smashcode.api.agroconnect.dto.curtida.GetRequestCurtida;
import br.com.smashcode.api.agroconnect.exception.dto.BadRequestException;
import br.com.smashcode.api.agroconnect.model.Curtida;
import br.com.smashcode.api.agroconnect.model.Postagem;
import br.com.smashcode.api.agroconnect.model.Usuario;
import br.com.smashcode.api.agroconnect.repository.CurtidaRepository;
import br.com.smashcode.api.agroconnect.repository.PostagemRepository;
import br.com.smashcode.api.agroconnect.repository.UsuarioRepository;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class CurtidaServiceImpl implements CurtidaService {

    @Autowired
    private CurtidaRepository curtidaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PostagemRepository postagemRepository;


    @Override
    public Page<GetRequestCurtida> findAll(Pageable pageable) {
        return curtidaRepository.findAll(pageable).map(GetRequestCurtida::new);
    }

    @Override
    public Page<GetRequestCurtida> findAllByPostagemOrElseThrowBadRequestException(String postagemId, Pageable pageable) {
        Postagem postagem = getPostagemOrElseThrowBadRequestException(postagemId);
        return curtidaRepository.findAllByPostagem(postagem, pageable).map(GetRequestCurtida::new);
    }

    @Override
    public GetRequestCurtida likePost(Usuario usuario, Postagem postagem) {

        Curtida curtida = curtidaRepository.findByUsuarioAndPostagem(usuario, postagem);

        if(curtida == null) {
            curtida = new Curtida();
            Usuario usuarioCurtida = getUsuarioOrElseThrowBadRequestException(usuario.getId());
            Postagem postagemCurtida = getPostagemOrElseThrowBadRequestException(postagem.getId());
            curtida.setUsuario(usuarioCurtida);
            curtida.setPostagem(postagemCurtida);
            curtida.prepararRegistro();
        }
        
        Curtida created = curtidaRepository.saveAndFlush(curtida);
        return toGetRequestCurtida(created);
    }

    @Override
    public void deslikePostByIdOrElseThrowBadRequestException(String id) {
        Curtida curtida = getCurtidaOrElseThrowBadRequestException(id);
        curtidaRepository.delete(curtida);
    }



    private Curtida getCurtidaOrElseThrowBadRequestException(String id) {
        return curtidaRepository.findById(id).orElseThrow(
            () -> new BadRequestException("Nenhuma curtida foi encontrada com esse id.")
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

    private GetRequestCurtida toGetRequestCurtida(Curtida curtida) {
        return new GetRequestCurtida(curtida);
    }
    
}
