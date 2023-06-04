package br.com.smashcode.api.agroconnect.service.auth.token;

import br.com.smashcode.api.agroconnect.dto.jwt.TokenUsuario;
import br.com.smashcode.api.agroconnect.dto.usuario.AuthDataUsuario;
import br.com.smashcode.api.agroconnect.model.Usuario;

public interface TokenService {
    
    public TokenUsuario generateToken(AuthDataUsuario usuario);
    public Usuario getUsuarioByToken(String token);

}
