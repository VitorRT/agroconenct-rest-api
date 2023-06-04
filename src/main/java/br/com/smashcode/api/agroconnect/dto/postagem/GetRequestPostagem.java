package br.com.smashcode.api.agroconnect.dto.postagem;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.hateoas.Links;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.smashcode.api.agroconnect.dto.usuario.UsuarioRelation;
import br.com.smashcode.api.agroconnect.model.Comentario;
import br.com.smashcode.api.agroconnect.model.Curtida;
import br.com.smashcode.api.agroconnect.model.Postagem;
import lombok.Data;

@Data
public class GetRequestPostagem {
     
    private String id;

    private UsuarioRelation usuario;

    @JsonIgnore
    private Set<Curtida> curtidas = new HashSet<Curtida>();
    
    @JsonIgnore
    private Set<Comentario> comentarios = new HashSet<Comentario>();
    
    private String titulo;


    private String conteudo;

    @JsonProperty(value="tipo_postagem")
    private String tipoPostagem;

    @JsonProperty(value = "data_criacao")
    private LocalDateTime dataCriacao;

    @JsonProperty(value = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    private Links links;

    public GetRequestPostagem(Postagem postagem) {
        this.id = postagem.getId();
        this.usuario = UsuarioRelation.tpUsuarioRelation(postagem.getUsuario());
        this.curtidas = postagem.getCurtidas();
        this.comentarios = postagem.getComentarios();
        this.titulo = postagem.getTitulo();
        this.conteudo = postagem.getConteudo();
        this.tipoPostagem = postagem.getTipoPostagem();
        this.dataCriacao = postagem.getDataCriacao();
        this.dataAtualizacao = postagem.getDataAtualizacao();
        this.links = postagem.toEntityModel().getLinks();
    }
}
