package br.com.minhascontas.controller;

import br.com.minhascontas.model.Cartao;
import br.com.minhascontas.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cartoes")
@CrossOrigin(origins = "*")
public class CartaoController {

    @Autowired
    private CartaoService service;

    @GetMapping
    public List<Cartao> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cartao> findById(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Cartao save(@RequestBody Cartao cartao) {
        return service.save(cartao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
