package br.com.smashcode.api.agroconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.smashcode.api.agroconnect.dto.postagem.GetRequestPostagem;
import br.com.smashcode.api.agroconnect.model.Postagem;
import br.com.smashcode.api.agroconnect.service.postagem.PostagemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/postagem")
@Tag(name="Postagem 📝")
public class PostagemController {
    
    @Autowired
    private PostagemService postagemService;

    @Autowired
    PagedResourcesAssembler<GetRequestPostagem> assembler;

    @GetMapping
    @Operation(
        summary = "Listagem de postagens.",
        description = "Listagem geral de todas as postagens cadastradas e ativas. Porém esse método entrega mais do que uma simples listagem, você pode passar um parâmetro de busca chamado \"?search=\" que a api irá procurar postagens pelo titulo. Além disso você pode passar parâmetros para a paginação como \"?size=\" para controlar o numero de postagens em uma página e o \"?page=\" para controlar a página."
    )
    @ApiResponses( {
        @ApiResponse(responseCode = "200", description = "Os dados das postagens foram retornados.")
    }
    )
    public PagedModel<EntityModel<GetRequestPostagem>> search(@PageableDefault(size=5) Pageable pageable, @RequestParam(required=false) String search) {
        return assembler.toModel(postagemService.searchAll(pageable, search));
    }

    @PostMapping
    @Operation(
        summary = "Cadastro de postagem.",
        description = "Cadastro de uma postagem."
    )
    @ApiResponses( {
        @ApiResponse(responseCode = "201", description = "A postagem foi criada com sucesso."),
        @ApiResponse(responseCode = "400", description = "Os dados enviados são inválidos.")
    }
    )
    public ResponseEntity<GetRequestPostagem> create(@RequestBody Postagem postagem) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postagemService.save(postagem));
    }

    @GetMapping("{id}")
    @Operation(
        summary = "Detalhamento de postagem.",
        description = "Detalhamento de uma postagem cadastrada e ativa."
    )
    @ApiResponses( {
        @ApiResponse(responseCode = "200", description = "Os dados da postagem foram retornados."),
        @ApiResponse(responseCode = "400", description = "Não existe uma postagem com esse ID.")
    }
    )
    public ResponseEntity<GetRequestPostagem> show(@PathVariable String id) {
        return ResponseEntity.ok(postagemService.findByIdOrElseThrowBadRequestExcepetion(id));
    }

    @PutMapping("{id}")
    @Operation(
        summary = "Edição de postagem.",
        description = "Edição de uma postagem cadastrada e ativa. É preciso informar o id da postagem no path da requisição e um corpo na requisição."
    )
    @ApiResponses( {
        @ApiResponse(responseCode = "200", description = "Os dados da postagem foram retornados."),
        @ApiResponse(responseCode = "400", description = "Não existe uma postagem com esse ID.")
    }
    )
    public ResponseEntity<GetRequestPostagem> update(@PathVariable String id, @RequestBody Postagem postagem) {
        return ResponseEntity.ok(postagemService.updateByIdOrElseThrowBadRequestException(id, postagem));
    }
    
    @DeleteMapping("{id}")
    @Operation(
        summary = "Deleção de postagem.",
        description = "Deleção de uma postagem cadastrada e ativa. É preciso informar o id da postagem no path da requisição."
    )
    @ApiResponses( {
        @ApiResponse(responseCode = "204", description = "A postagem foi deletada com sucesso."),
        @ApiResponse(responseCode = "400", description = "Não existe uma postagem com esse ID.")
    }
    )
    public ResponseEntity<Void> destroy(@PathVariable String id) {
        postagemService.deleteByIdOrElseThrowBadRequestException(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("usuario/{usuarioId}")
    @Operation(
        summary = "Listagem de todas as postagens de um usuário.",
        description = "Listagem geral de todas as postagens cadastradas e ativas de um usuário. Porém esse método entrega mais do que uma simples listagem, você pode passar um parâmetro de busca chamado \"?search=\" que a api irá procurar postagens pelo titulo. Além disso você pode passar parâmetros para a paginação como \"?size=\" para controlar o numero de postagens em uma página e o \"?page=\" para controlar a página. Você precisa passar o id do usuário no path da requisição."
    )
    @ApiResponses( {
        @ApiResponse(responseCode = "200", description = "Os dados das postagens foram retornados.")
    }
    )
    public PagedModel<EntityModel<GetRequestPostagem>> searchByUsuario(@PathVariable String usuarioId, @PageableDefault(size=5) Pageable pageable) {
        return assembler.toModel(postagemService.findAllByUsuario(usuarioId, pageable));
    }

}
