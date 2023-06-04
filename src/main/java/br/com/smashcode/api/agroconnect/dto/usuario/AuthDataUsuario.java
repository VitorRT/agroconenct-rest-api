package br.com.smashcode.api.agroconnect.dto.usuario;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public record AuthDataUsuario(String email, String senha) {
    
    public Authentication toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email,senha);
    }
}
