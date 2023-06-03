package br.com.smashcode.api.agroconnect.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="postagem")
@Data
@EqualsAndHashCode(of={"id"})
public class Postagem {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @ManyToOne
    @JoinColumn(name="usuario_fk")
    @NotNull(message="a chave estrangeira do usuário não pode ser nula.")
    private Usuario usuario;

    @OneToMany(mappedBy="postagem", cascade=CascadeType.ALL)
    @JsonIgnore
    private Set<Curtida> curtidas = new HashSet<Curtida>();
    
    @OneToMany(mappedBy="postagem", cascade=CascadeType.ALL)
    @JsonIgnore
    private Set<Comentario> comentarios = new HashSet<Comentario>();

    @Column(length=150)
    @NotBlank(message="o titulo da postagem não pode ser nulo ou vazio.")
    @Size(min=8, max=150, message="O titulo da postagem deve ter entre 8 e 150 caracteres.")
    private String titulo;

    @Lob
    @NotBlank(message="o conteudo da postagem não pode ser nulo ou vazio.")
    private String conteudo;

    @Column(name="tipo_postagem", length=40)
    @JsonProperty(value="tipo_postagem")
    @NotBlank(message="o tipo de postagem não pode ser nulo ou vazio.")
    private String tipoPostagem;


    @Column(name="data_criacao")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty(value="data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name="data_atualizacao")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty(value="data_atualizacao")
    private LocalDateTime dataAtualizacao;


    public void prepararRegistro() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    public void prepararAtualizacao(Postagem postagem) {
        this.id = postagem.getId();
        this.dataAtualizacao = LocalDateTime.now();
        this.dataCriacao = postagem.getDataCriacao();
        this.usuario = postagem.getUsuario();
    }

}
