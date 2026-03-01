package br.com.minhascontas.controller;

import br.com.minhascontas.dto.card.CardFindRes;
import br.com.minhascontas.dto.card.CardSaveReq;
import br.com.minhascontas.dto.card.CardSaveRes;
import br.com.minhascontas.model.Banco;
import br.com.minhascontas.model.Cartao;
import br.com.minhascontas.model.Usuario;
import br.com.minhascontas.service.CartaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService service;

    @GetMapping
    public ResponseEntity<List<CardFindRes>> findAll(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<CardFindRes> result = service.findAll(start, end).stream()
                .map(CardFindRes::fromEntity)
                .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardFindRes> findById(@PathVariable UUID id) {
        return service.findById(id)
                .map(CardFindRes::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CardSaveRes> save(@Valid @RequestBody CardSaveReq req) {
        Usuario user = service.findUserById(req.getUserId());
        Banco bank = service.findBankById(req.getBankId());
        Cartao saved = service.save(req.toEntity(user, bank));
        return ResponseEntity.ok(CardSaveRes.fromEntity(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
