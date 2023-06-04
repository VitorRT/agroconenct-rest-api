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

@RestController
@RequestMapping("/postagem")
public class PostagemController {
    
    @Autowired
    private PostagemService postagemService;

    @Autowired
    PagedResourcesAssembler<GetRequestPostagem> assembler;

    @GetMapping
    public PagedModel<EntityModel<GetRequestPostagem>> search(@PageableDefault(size=5) Pageable pageable, @RequestParam(required=false) String search) {
        return assembler.toModel(postagemService.searchAll(pageable, search));
    }

    @PostMapping
    public ResponseEntity<GetRequestPostagem> create(@RequestBody Postagem postagem) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postagemService.save(postagem));
    }

    @GetMapping("{id}")
    public ResponseEntity<GetRequestPostagem> show(@PathVariable String id) {
        return ResponseEntity.ok(postagemService.findByIdOrElseThrowBadRequestExcepetion(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<GetRequestPostagem> update(@PathVariable String id, @RequestBody Postagem postagem) {
        return ResponseEntity.ok(postagemService.updateByIdOrElseThrowBadRequestException(id, postagem));
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<Void> destroy(@PathVariable String id) {
        postagemService.deleteByIdOrElseThrowBadRequestException(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("usuario/{usuarioId}")
    public PagedModel<EntityModel<GetRequestPostagem>> searchByUsuario(@PathVariable String usuarioId, @PageableDefault(size=5) Pageable pageable) {
        return assembler.toModel(postagemService.findAllByUsuario(usuarioId, pageable));
    }

}
