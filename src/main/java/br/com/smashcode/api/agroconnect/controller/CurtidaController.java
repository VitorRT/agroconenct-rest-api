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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.smashcode.api.agroconnect.dto.curtida.GetRequestCurtida;
import br.com.smashcode.api.agroconnect.model.Curtida;
import br.com.smashcode.api.agroconnect.service.curtida.CurtidaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/curtida")
@Tag(name="Curtida 👍")
public class CurtidaController {
    @Autowired
    private CurtidaService curtidaService;

    @Autowired
    PagedResourcesAssembler<GetRequestCurtida> assembler;

    @GetMapping
    @Operation(
        summary = "Listagem de curtidas.",
        description = "Listagem geral de todas as curtidas cadastradas e ativas. Além disso você pode passar parâmetros para a paginação como \"?size=\" para controlar o numero de curtidas em uma página e o \"?page=\" para controlar a página."
    )
    @ApiResponses( {
        @ApiResponse(responseCode = "200", description = "Os dados das curtidas foram retornados.")
    }
    )
    public PagedModel<EntityModel<GetRequestCurtida>> search(@PageableDefault(size=5) Pageable pageable) {
        return assembler.toModel(curtidaService.findAll(pageable));
    }

    @GetMapping("postagem/{postagemId}")
    @Operation(
        summary = "Listagem de todas as curtidas de uma postagem.",
        description = "Listagem geral de todas as curtidas cadastradas e ativas de uma postagem. Além disso você pode passar parâmetros para a paginação como \"?size=\" para controlar o numero de curtidas em uma página e o \"?page=\" para controlar a página. Você precisa informar o id da postagem no path da requisição."
    )
    @ApiResponses( {
        @ApiResponse(responseCode = "200", description = "Os dados das curtidas foram retornados.")
    }
    )
    public PagedModel<EntityModel<GetRequestCurtida>> searchByPostagem(@PathVariable String postagemId, @PageableDefault(size=5) Pageable pageable) {
        return assembler.toModel(curtidaService.findAllByPostagemOrElseThrowBadRequestException(postagemId,pageable));
    }

    @PostMapping
    @Operation(
        summary = "Curtir uma postagem.",
        description = "Cadastro de uma curtida em uma postagem. O id da postagem e do usuário devem estar no corpo da requisição."
    )
    @ApiResponses( {
        @ApiResponse(responseCode = "201", description = "A curtida foi criada com sucesso."),
        @ApiResponse(responseCode = "400", description = "Os dados enviados são inválidos.")
    }
    )
    public ResponseEntity<GetRequestCurtida> likePost(@RequestBody Curtida curtida) {
        return ResponseEntity.status(HttpStatus.CREATED).body(curtidaService.likePost(curtida.getUsuario(), curtida.getPostagem()));
    }

    @DeleteMapping("{id}")
    @Operation(
        summary = "Descurtir uma postagem.",
        description = "Descurtir de uma postagem. Você precisa informar o id da curtida no path da requisição."
    )
    @ApiResponses( {
        @ApiResponse(responseCode = "200", description = "A curtida foi deletada com sucesso."),
        @ApiResponse(responseCode = "400", description = "Não existe uma curtida com esse ID.")
    }
    )
    public ResponseEntity<Void> deslikePost(@PathVariable String id) {
        curtidaService.deslikePostByIdOrElseThrowBadRequestException(id);
        return ResponseEntity.noContent().build();
    }
}
