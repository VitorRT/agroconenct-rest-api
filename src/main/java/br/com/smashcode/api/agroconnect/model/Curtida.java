package br.com.smashcode.api.agroconnect.model;

import java.time.LocalDateTime;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.smashcode.api.agroconnect.controller.CurtidaController;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="curtida")
@Data
@EqualsAndHashCode(of={"id"})
public class Curtida {
    
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name="usuario_fk")
    @NotNull(message="a chave estrangeira do usuário não pode ser nula.")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name="postagem_fk")
    @NotNull(message="a chave estrangeira da postagem não pode ser nula.")
    private Postagem postagem;

    private Boolean curtida;

    @Column(name="data_criacao")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty(value="data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name="data_atualizacao")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty(value="data_atualizacao")
    private LocalDateTime dataAtualizacao;
    
    @Column(name="ultima_curtida")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty(value="ultima_curtida")
    private LocalDateTime ultimaCurtida;


    public Curtida() {
        this.curtida = true;
    }

     public void prepararRegistro() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
        this.ultimaCurtida = LocalDateTime.now();
    }

    public void curtir() {
        if(!curtida) {
            this.curtida = true; 
            this.ultimaCurtida = LocalDateTime.now();
        }
    }

    public void descurtir() {
        if(curtida) {
            this.curtida = false;
            this.dataAtualizacao = LocalDateTime.now();
        }
    }

    public EntityModel<Curtida> toEntityModel() {
        Curtida c = new Curtida();
        return EntityModel.of(this,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CurtidaController.class).likePost(c))
                        .withRel("like"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CurtidaController.class).deslikePost(id))
                        .withRel("deslike"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CurtidaController.class).likePost(c))
                        .withRel("all likes on a post"),        
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CurtidaController.class).search(Pageable.unpaged()))
                        .withRel("all"));
    }

}
