package br.com.smashcode.api.agroconnect.dto.usuario;

import java.time.LocalDateTime;

import org.springframework.hateoas.Links;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.smashcode.api.agroconnect.model.Usuario;
import lombok.Data;

@Data
public class GetRequestUsuario {
 
    private String id;

    private String nome;

    private String email;

    @JsonProperty(value="numero_contato")
    private String numeroContato;

    @JsonProperty(value="data_criacao")
    private LocalDateTime dataCriacao;

    @JsonProperty(value = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    private Links links;

    public GetRequestUsuario(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.numeroContato = usuario.getNumeroContato();
        this.dataCriacao = usuario.getDataCriacao();
        this.dataAtualizacao = usuario.getDataAtualizacao();
        this.links = usuario.toEntityModel().getLinks();
    }
}
