package br.com.smashcode.api.agroconnect.dto.postagem;

import br.com.smashcode.api.agroconnect.model.Postagem;

public record PostagemRelation(String id, String titulo) {
    
    public PostagemRelation(Postagem postagem) {
        this(postagem.getId().toString(), postagem.getTitulo());
    }

    public static PostagemRelation toPostagemRelation(Postagem postagem) {
        return new PostagemRelation(postagem);
    }
}
