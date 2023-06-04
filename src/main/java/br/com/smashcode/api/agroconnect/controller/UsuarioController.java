package br.com.smashcode.api.agroconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.smashcode.api.agroconnect.dto.usuario.GetRequestUsuario;
import br.com.smashcode.api.agroconnect.model.Usuario;
import br.com.smashcode.api.agroconnect.service.usuario.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
@Tag(name="Usuário 🙋🏾‍♂️")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    PagedResourcesAssembler<GetRequestUsuario> assembler;



    @GetMapping
    @Operation(
        summary = "Listagem de contas.",
        description = "Listagem geral de todas os usuários cadastrados e ativos. Porém esse método entrega mais do que uma simples listagem, você pode passar um parâmetro de busca chamado \"?search=\" que a api irá procurar usuários pelo nome. Além disso você pode passar parâmetros para a paginação como \"?size=\" para controlar o numero de usuarios em uma página e o \"?page=\" para controlar a página."
    )
    @ApiResponses( {
        @ApiResponse(responseCode = "200", description = "Os dados dos usuários foram retornados.")
    }
    )
    public PagedModel<EntityModel<GetRequestUsuario>> search(@PageableDefault(size=5) Pageable pageable, @RequestParam(required = false) String search) {
        Page<GetRequestUsuario> page = usuarioService.searchAll(pageable, search);
        return assembler.toModel(page);
    }

    @PostMapping("signup")
    @Operation(
        summary = "Cadastro de conta.",
        description = "Cadastro de uma conta para poder acessar todos os recursos da api. (Esta requisição não precisa de um token.)"
    )
    @ApiResponses( {
        @ApiResponse(responseCode = "201", description = "A conta foi criada com sucesso."),
        @ApiResponse(responseCode = "400", description = "Os dados enviados são inválidos.")
    }
    )
    public ResponseEntity<GetRequestUsuario> create(@RequestBody @Valid Usuario usuario, BindingResult result) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));
    }

    @GetMapping("{id}")
    @Operation(
        summary = "Detalhamento de conta.",
        description = "Detalhamento de um usuário cadastrado e ativo."
    )
    @ApiResponses( {
        @ApiResponse(responseCode = "200", description = "Os dados do usuário foram retornados."),
        @ApiResponse(responseCode = "400", description = "Não existe um usuário com esse ID.")
    }
    )
    public ResponseEntity<GetRequestUsuario> show(@PathVariable String id) {
        return ResponseEntity.ok(usuarioService.findByIdOrElseThrowBadRequestExcepetion(id));
    }

    @PutMapping("{id}")
    @Operation(
        summary = "Edição de conta.",
        description = "Edição de um usuário cadastrado e ativo. É preciso informar o id do usuário no path da requisição e um corpo na requisição."
    )
    @ApiResponses( {
        @ApiResponse(responseCode = "200", description = "Os dados do usuário foram retornados."),
        @ApiResponse(responseCode = "400", description = "Não existe um usuário com esse ID.")
    }
    )
    public ResponseEntity<GetRequestUsuario> update(@PathVariable String id, @RequestBody @Valid Usuario usuario,  BindingResult result) {
        return ResponseEntity.ok(usuarioService.updateByIdOrElseThrowBadRequestException(id, usuario));
    }

    @DeleteMapping("{id}")
    @Operation(
        summary = "Deleção de conta.",
        description = "Deleção de um usuário cadastrado e ativo. É preciso informar o id do cliente no path da requisição."
    )
    @ApiResponses( {
        @ApiResponse(responseCode = "204", description = "O usuário foi deletado com sucesso."),
        @ApiResponse(responseCode = "400", description = "Não existe um usuário com esse ID.")
    }
    )
    public ResponseEntity<Void> destroy(@PathVariable String id) {
        usuarioService.deleteByIdOrElseThrowBadRequestException(id);
        return ResponseEntity.noContent().build();
    }

}
