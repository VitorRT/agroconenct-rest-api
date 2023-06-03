package br.com.smashcode.api.agroconnect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/curtida")
public class CurtidaController {
    @Autowired
    private CurtidaService curtidaService;

    @GetMapping
    public ResponseEntity<List<GetRequestCurtida>> search() {
        return ResponseEntity.ok(curtidaService.findAll());
    }

    @GetMapping("postagem/{postagemId}")
    public ResponseEntity<List<GetRequestCurtida>> searchByPostagem(@PathVariable String postagemId) {
        return ResponseEntity.ok(curtidaService.findAllByPostagemOrElseThrowBadRequestException(postagemId));
    }

    @PostMapping
    public ResponseEntity<GetRequestCurtida> likePost(@RequestBody Curtida curtida) {
        return ResponseEntity.status(HttpStatus.CREATED).body(curtidaService.likePost(curtida.getUsuario(), curtida.getPostagem()));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deslikePost(@PathVariable String id) {
        curtidaService.deslikePostByIdOrElseThrowBadRequestException(id);
        return ResponseEntity.noContent().build();
    }
}
