package br.com.smashcode.api.agroconnect.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.smashcode.api.agroconnect.controller.UsuarioController;
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
public class Usuario implements UserDetails {

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

    @Column(name="numero_contato", length=14)
    @Size(min=14, message="O numero de contato do usuario deve ter 14 caracteres.")
    @JsonProperty(value="numero_contato")
    private String numeroContato;

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

    
    public EntityModel<Usuario> toEntityModel() {
        return EntityModel.of(this,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).show(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).destroy(id))
                        .withRel("delete"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).search(Pageable.unpaged(), ""))
                        .withRel("all"));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
