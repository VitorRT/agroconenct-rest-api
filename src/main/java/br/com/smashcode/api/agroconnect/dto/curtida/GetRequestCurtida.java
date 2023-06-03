package br.com.smashcode.api.agroconnect.dto.curtida;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.smashcode.api.agroconnect.dto.postagem.PostagemRelation;
import br.com.smashcode.api.agroconnect.dto.usuario.UsuarioRelation;
import br.com.smashcode.api.agroconnect.model.Curtida;
import lombok.Data;

@Data
public class GetRequestCurtida {

    private String id;

    private UsuarioRelation usuario;

    private PostagemRelation postagem;

    private Boolean curtida;

    @JsonProperty(value = "data_criacao")
    private LocalDateTime dataCriacao;
   
    @JsonProperty(value = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
    
    @JsonProperty(value = "ultima_curtida")
    private LocalDateTime ultimaCurtida;

    public GetRequestCurtida(Curtida curtida) {
        this.id = curtida.getId();
        this.usuario = UsuarioRelation.tpUsuarioRelation(curtida.getUsuario());
        this.postagem = PostagemRelation.toPostagemRelation(curtida.getPostagem());
        this.curtida = curtida.getCurtida();
        this.dataCriacao = curtida.getDataCriacao();
        this.dataAtualizacao = curtida.getDataAtualizacao();
        this.ultimaCurtida = curtida.getUltimaCurtida();
    }
}
