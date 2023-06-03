package br.com.smashcode.api.agroconnect.service.curtida;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<GetRequestCurtida> findAll() {
        List<Curtida> curtidas = curtidaRepository.findAll();
        return curtidas.stream().map(GetRequestCurtida::new).toList();
    }

    @Override
    public List<GetRequestCurtida> findAllByPostagemOrElseThrowBadRequestException(String postagemId) {
        Postagem postagem = getPostagemOrElseThrowBadRequestException(postagemId);
        List<Curtida> curtidas = curtidaRepository.findAllByPostagem(postagem); 
        return curtidas.stream().map(GetRequestCurtida::new).toList();
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
