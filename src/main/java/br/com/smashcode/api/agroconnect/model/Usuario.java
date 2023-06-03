package br.com.smashcode.api.agroconnect.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(
    name="usuario", 
    uniqueConstraints=@UniqueConstraint(columnNames={"email"})
)
@Data
@EqualsAndHashCode(of={"id","email"})
public class Usuario {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @ManyToMany
    @JoinTable(
        name="seguidor",
        joinColumns = @JoinColumn(name="usuario_fk"),
        inverseJoinColumns = @JoinColumn(name="seguidor_fk")
    )
    private Set<Usuario> usuarios = new HashSet<Usuario>();

    @OneToMany(mappedBy="usuario", cascade=CascadeType.ALL)
    @JsonIgnore
    private Set<Postagem> postagens = new HashSet<Postagem>();

    @OneToMany(mappedBy="usuario", cascade=CascadeType.ALL)
    @JsonIgnore
    private Set<Curtida> curtidas = new HashSet<Curtida>();

    @OneToMany(mappedBy="usuario", cascade=CascadeType.ALL)
    @JsonIgnore
    private Set<Comentario> comentarios = new HashSet<Comentario>();

    @Column(length=80)
    @NotBlank(message="o nome do usuário não pode ser nulo ou vazio.")
    @Size(min=4, max=80, message="O nome do usuario deve ter entre 4 e 80 caracteres.")
    private String nome;
    
    @Column(length=80)
    @Email(message="email inválido.")
    @NotBlank(message="o email do usuário não pode ser nulo ou vazio.")
    @Size(min=4, max=80, message="O email do usuario deve ter entre 4 e 80 caracteres.")
    private String email;

    @Column(length=150)
    @JsonProperty(access = Access.WRITE_ONLY)
    @NotBlank(message="a senha do usuário não pode ser nula ou vazia.")
    @Size(min=4, message = "senha muito pequena.")
    private String senha;

    @Column(name="data_criacao")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty(value="data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name="data_atualizacao")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty(value = "data_atualizacao")
    private LocalDateTime dataAtualizacao;


    public void prepararRegistro() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    public void prepararAtualizacao(Usuario usuario) {
        this.id = usuario.getId();
        this.dataAtualizacao = LocalDateTime.now();
        this.dataCriacao = usuario.getDataCriacao();
    };

}
