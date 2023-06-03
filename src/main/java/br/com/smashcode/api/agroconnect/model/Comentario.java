package br.com.smashcode.api.agroconnect.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="comentario")
@Data
@EqualsAndHashCode(of={"id"})
public class Comentario {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @ManyToOne
    @JoinColumn(name="usuario_fk")
    @NotNull(message="a chave estrangeira do usuário não pode ser nula.")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name="postagem_fk")
    @NotNull(message="a chave estrangeira da postagem não pode ser nula.")
    private Postagem postagem;

    @Lob
    @NotBlank(message="o conteudo do comentário não pode ser vazio ou nulo.")
    private String conteudo;
   
    @Column(name = "data_criacao")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty(value = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty(value = "data_atualizacao")
    private LocalDateTime dataAtualizacao;


    public void prepararRegistro() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    public void prepararAtualizacao(Comentario comentario) {

        this.id = comentario.getId();
        this.dataAtualizacao = LocalDateTime.now();
        this.dataCriacao = comentario.getDataCriacao();
        this.usuario = comentario.getUsuario();
        this.postagem = comentario.getPostagem(); 
        
    }

}

