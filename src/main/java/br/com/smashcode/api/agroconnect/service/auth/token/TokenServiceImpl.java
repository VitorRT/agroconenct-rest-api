package br.com.smashcode.api.agroconnect.service.auth.token;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.smashcode.api.agroconnect.dto.jwt.TokenUsuario;
import br.com.smashcode.api.agroconnect.dto.usuario.AuthDataUsuario;
import br.com.smashcode.api.agroconnect.model.Usuario;
import br.com.smashcode.api.agroconnect.repository.UsuarioRepository;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public TokenUsuario generateToken (AuthDataUsuario usuario) {
        Algorithm alg = Algorithm.HMAC256("secret");
        var token = JWT.create()
                    .withSubject(usuario.email())
                    .withIssuer("Agro Connect")
                    .withExpiresAt(Instant.now().plus(40, ChronoUnit.MINUTES))
                    .sign(alg)
                    ;

        return new TokenUsuario(token, "JWT", "Bearer","Bearer " + token);
    }

    @Override
    public Usuario getUsuarioByToken(String token) {
        Algorithm alg = Algorithm.HMAC256("secret");
        var email = JWT.require(alg)
                    .withIssuer("Agro Connect")
                    .build()
                    .verify(token)
                    .getSubject()
                    ;
        return usuarioRepository.findByEmail(email).orElseThrow(
            () -> new JWTVerificationException("Usuário inválido.")
        );
    }
    
}
