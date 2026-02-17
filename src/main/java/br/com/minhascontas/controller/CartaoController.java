package br.com.minhascontas.controller;

import br.com.minhascontas.model.Cartao;
import br.com.minhascontas.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cartoes")

public class CartaoController {

    @Autowired
    private CartaoService service;

    @GetMapping
    public ResponseEntity<List<Cartao>> findAll(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        return ResponseEntity.ok(service.findAll(start, end));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cartao> findById(@PathVariable UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cartao> save(@RequestBody Cartao cartao) {
        return ResponseEntity.ok(service.save(cartao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
