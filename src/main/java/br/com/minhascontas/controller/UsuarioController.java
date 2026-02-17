package br.com.minhascontas.controller;

import br.com.minhascontas.model.Usuario;
import br.com.minhascontas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")

public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public ResponseEntity<List<Usuario>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Usuario> save(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(service.save(usuario));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
