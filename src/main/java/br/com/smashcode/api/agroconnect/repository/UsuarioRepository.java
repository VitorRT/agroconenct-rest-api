package br.com.smashcode.api.agroconnect.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.smashcode.api.agroconnect.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    
    Page<Usuario> findByNomeContaining(Pageable pageable,String search);
}
