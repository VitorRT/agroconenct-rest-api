package br.com.smashcode.api.agroconnect.dto.comentario;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.smashcode.api.agroconnect.dto.postagem.PostagemRelation;
import br.com.smashcode.api.agroconnect.dto.usuario.UsuarioRelation;
import br.com.smashcode.api.agroconnect.model.Comentario;
import lombok.Data;

@Data
public class GetRequestComentario {

    private String id;

    private UsuarioRelation usuario;

    private PostagemRelation postagem;

    private String conteudo;
   
    @JsonProperty(value = "data_criacao")
    private LocalDateTime dataCriacao;

    @JsonProperty(value = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    public GetRequestComentario(Comentario comentario) {
        this.id = comentario.getId();
        this.usuario = UsuarioRelation.tpUsuarioRelation(comentario.getUsuario());
        this.postagem = PostagemRelation.toPostagemRelation(comentario.getPostagem());
        this.conteudo = comentario.getConteudo();
        this.dataCriacao = comentario.getDataCriacao();
        this.dataAtualizacao = comentario.getDataAtualizacao();
    }
}
