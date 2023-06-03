package br.com.smashcode.api.agroconnect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.smashcode.api.agroconnect.model.Comentario;
import br.com.smashcode.api.agroconnect.model.Postagem;

public interface ComentarioRepository extends JpaRepository<Comentario, String>{
    
    List<Comentario> findAllByPostagem(Postagem postagem);
}
