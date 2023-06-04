package br.com.smashcode.api.agroconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.smashcode.api.agroconnect.dto.jwt.TokenUsuario;
import br.com.smashcode.api.agroconnect.dto.usuario.AuthDataUsuario;
import br.com.smashcode.api.agroconnect.service.auth.token.TokenService;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;
 
    @PostMapping
    public ResponseEntity<TokenUsuario> authentication(@RequestBody AuthDataUsuario authUsuario, BindingResult result) {
        authManager.authenticate(authUsuario.toAuthentication());
        var token = tokenService.generateToken(authUsuario);
        return ResponseEntity.ok(token);
    }
}
