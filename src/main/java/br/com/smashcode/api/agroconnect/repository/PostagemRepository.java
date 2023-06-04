package br.com.smashcode.api.agroconnect.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.smashcode.api.agroconnect.model.Postagem;
import br.com.smashcode.api.agroconnect.model.Usuario;

public interface PostagemRepository extends JpaRepository<Postagem, String> {
    
    public Page<Postagem> findAllByUsuario(Usuario usuario, Pageable pageable);
    public Page<Postagem> findByTituloContaining(Pageable pageable, String search);
}
