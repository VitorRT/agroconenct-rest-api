package br.com.smashcode.api.agroconnect.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.smashcode.api.agroconnect.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    
    public Page<Usuario> findByNomeContaining(Pageable pageable,String search);
    public Optional<Usuario> findByEmail(String email);
}
