package br.com.smashcode.api.agroconnect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.smashcode.api.agroconnect.dto.comentario.GetRequestComentario;
import br.com.smashcode.api.agroconnect.model.Comentario;
import br.com.smashcode.api.agroconnect.service.comentario.ComentarioService;

@RestController
@RequestMapping("/comentario")
public class ComentarioController {
    
    @Autowired
    private ComentarioService comentarioService;

    @GetMapping
    public ResponseEntity<List<GetRequestComentario>> search() {
        return ResponseEntity.ok(comentarioService.findAll());
    }

    @PostMapping
    public ResponseEntity<GetRequestComentario> create(@RequestBody Comentario comentario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(comentarioService.save(comentario));
    }

    @GetMapping("{id}")
    public ResponseEntity<GetRequestComentario> show(@PathVariable String id) {
        return ResponseEntity.ok(comentarioService.findByIdOrElseThrowBadRequestExcepetion(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<GetRequestComentario> update(@PathVariable String id, @RequestBody Comentario comentario) {
        return ResponseEntity.ok(comentarioService.updateByIdOrElseThrowBadRequestException(id, comentario));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> destroy(@PathVariable String id) {
        comentarioService.deleteByIdOrElseThrowBadRequestException(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("postagem/{postagemId}")
    public ResponseEntity<List<GetRequestComentario>> searchByPostagem(@PathVariable String postagemId) {
        return ResponseEntity.ok(comentarioService.findAllByPostagem(postagemId));
    }
}
