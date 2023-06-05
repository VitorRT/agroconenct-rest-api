package br.com.smashcode.api.agroconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.smashcode.api.agroconnect.dto.jwt.TokenUsuario;
import br.com.smashcode.api.agroconnect.dto.usuario.AuthDataUsuario;
import br.com.smashcode.api.agroconnect.service.auth.token.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/login")
@CrossOrigin(
    origins={"*"}, 
    allowedHeaders={"*"},
    originPatterns={"*"}, 
    methods={
        RequestMethod.GET,
        RequestMethod.POST, 
        RequestMethod.PUT,
        RequestMethod.DELETE
    }
)
@Tag(name="Auth 🔐")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;
 
    @PostMapping
    @Operation(
        summary = "Login",
        description = "Autenticação para poder receber um token JWT e usar os recursos da api. (Esta requisição não precisa de um token)."
    )
    @ApiResponses( {
        @ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso."),
        @ApiResponse(responseCode = "403", description = "Os dados de autenticação são iválidos.")
    }
    )
    public ResponseEntity<TokenUsuario> authentication(@RequestBody AuthDataUsuario authUsuario, BindingResult result) {
        authManager.authenticate(authUsuario.toAuthentication());
        var token = tokenService.generateToken(authUsuario);
        return ResponseEntity.ok(token);
    }
}
