package br.com.smashcode.api.agroconnect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.smashcode.api.agroconnect.model.Curtida;
import br.com.smashcode.api.agroconnect.model.Postagem;
import br.com.smashcode.api.agroconnect.model.Usuario;

public interface CurtidaRepository extends JpaRepository<Curtida, String> {
    
    public Curtida findByUsuarioAndPostagem(Usuario usuario, Postagem postagem);
    public List<Curtida> findAllByPostagem(Postagem postagem);
}
