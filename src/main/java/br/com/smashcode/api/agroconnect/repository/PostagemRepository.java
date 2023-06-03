package br.com.smashcode.api.agroconnect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.smashcode.api.agroconnect.model.Postagem;
import br.com.smashcode.api.agroconnect.model.Usuario;

public interface PostagemRepository extends JpaRepository<Postagem, String> {
    
    public List<Postagem> findAllByUsuario(Usuario usuario);
}
