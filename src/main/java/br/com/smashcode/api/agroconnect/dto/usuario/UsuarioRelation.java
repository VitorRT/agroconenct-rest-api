package br.com.smashcode.api.agroconnect.dto.usuario;

import br.com.smashcode.api.agroconnect.model.Usuario;

public record UsuarioRelation(String id, String nome) {
    
    public UsuarioRelation(Usuario usuario) {
        this(usuario.getId().toString(), usuario.getNome());
    }

    public static UsuarioRelation tpUsuarioRelation(Usuario usuario) {
        return new UsuarioRelation(usuario);
    }
}
