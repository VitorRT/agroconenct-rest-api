package br.com.smashcode.api.agroconnect.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

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

}
