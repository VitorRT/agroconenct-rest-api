package br.com.smashcode.api.agroconnect.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.smashcode.api.agroconnect.model.Curtida;
import br.com.smashcode.api.agroconnect.model.Postagem;
import br.com.smashcode.api.agroconnect.model.Usuario;

public interface CurtidaRepository extends JpaRepository<Curtida, String> {
    
    public Curtida findByUsuarioAndPostagem(Usuario usuario, Postagem postagem);
    public Page<Curtida> findAllByPostagem(Postagem postagem, Pageable pageable);
}
