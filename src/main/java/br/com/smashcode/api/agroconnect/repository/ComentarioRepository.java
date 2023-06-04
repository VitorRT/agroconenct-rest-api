package br.com.smashcode.api.agroconnect.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.smashcode.api.agroconnect.model.Comentario;
import br.com.smashcode.api.agroconnect.model.Postagem;

public interface ComentarioRepository extends JpaRepository<Comentario, String>{
    
    Page<Comentario> findAllByPostagem(Postagem postagem, Pageable pageable);
}
