package br.com.smashcode.api.agroconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.smashcode.api.agroconnect.dto.comentario.GetRequestComentario;
import br.com.smashcode.api.agroconnect.model.Comentario;
import br.com.smashcode.api.agroconnect.service.comentario.ComentarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/comentario")
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
@Tag(name="Comentário 💬")
public class ComentarioController {
    
    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    PagedResourcesAssembler<GetRequestComentario> assembler;

    @GetMapping
    @Operation(
        summary = "Listagem de comentários.",
        description = "Listagem geral de todos os comentários cadastrados e ativos. Além disso você pode passar parâmetros para a paginação como \"?size=\" para controlar o numero de comentários em uma página e o \"?page=\" para controlar a página."
    )
    @ApiResponses( {
        @ApiResponse(responseCode = "200", description = "Os dados dos comentários foram retornados.")
    }
    )
    public PagedModel<EntityModel<GetRequestComentario>> search(@PageableDefault(size=5) Pageable pageable) {
        return assembler.toModel(comentarioService.findAll(pageable));
    }

    @PostMapping
    @Operation(
        summary = "Comentar em uma postagem.",
        description = "Cadastro de um comentário em uma postagem. O id da postagem e do usuário devem estar no corpo da requisição."
    )
    @ApiResponses( {
        @ApiResponse(responseCode = "201", description = "O comentário foi criado com sucesso."),
        @ApiResponse(responseCode = "400", description = "Os dados enviados são inválidos.")
    }
    )
    public ResponseEntity<GetRequestComentario> create(@RequestBody Comentario comentario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(comentarioService.save(comentario));
    }

    @GetMapping("{id}")
    @Operation(
        summary = "Detalhamento de comentário.",
        description = "Detalhamento de um comentário cadastrado e ativo."
    )
    @ApiResponses( {
        @ApiResponse(responseCode = "200", description = "Os dados do comentário foram retornados."),
        @ApiResponse(responseCode = "400", description = "Não existe um comentário com esse ID.")
    }
    )
    public ResponseEntity<GetRequestComentario> show(@PathVariable String id) {
        return ResponseEntity.ok(comentarioService.findByIdOrElseThrowBadRequestExcepetion(id));
    }

    @PutMapping("{id}")
    @Operation(
        summary = "Edição de comentário.",
        description = "Edição de um comentário cadastrado e ativo. É preciso informar o id do comentário no path da requisição e um corpo na requisição."
    )
    @ApiResponses( {
        @ApiResponse(responseCode = "200", description = "Os dados do comentário foram retornados."),
        @ApiResponse(responseCode = "400", description = "Não existe um comentário com esse ID.")
    }
    )
    public ResponseEntity<GetRequestComentario> update(@PathVariable String id, @RequestBody Comentario comentario) {
        return ResponseEntity.ok(comentarioService.updateByIdOrElseThrowBadRequestException(id, comentario));
    }

    @DeleteMapping("{id}")
    @Operation(
        summary = "Deleção de comentário.",
        description = "Deleção de um comentário cadastrado e ativo. É preciso informar o id do comentário no path da requisição."
    )
    @ApiResponses( {
        @ApiResponse(responseCode = "204", description = "O comentário foi deletado com sucesso."),
        @ApiResponse(responseCode = "400", description = "Não existe um comentário com esse ID.")
    }
    )
    public ResponseEntity<Void> destroy(@PathVariable String id) {
        comentarioService.deleteByIdOrElseThrowBadRequestException(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("postagem/{postagemId}")
    @Operation(
        summary = "Listagem de todos comentários de uma postagem.",
        description = "Listagem geral de todos os comentários cadastrados e ativos de uma postagem. Além disso você pode passar parâmetros para a paginação como \"?size=\" para controlar o numero de comentários em uma página e o \"?page=\" para controlar a página. Você precisa informar o id da postagem no path da requisição"
    )
    @ApiResponses( {
        @ApiResponse(responseCode = "200", description = "Os dados dos comentários foram retornados.")
    }
    )
    public PagedModel<EntityModel<GetRequestComentario>> searchByPostagem(@PathVariable String postagemId, @PageableDefault(size=5) Pageable pageable) {
        return assembler.toModel(comentarioService.findAllByPostagem(postagemId,pageable));
    }
}
