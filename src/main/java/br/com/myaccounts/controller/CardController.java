package br.com.myaccounts.controller;

import br.com.myaccounts.dto.card.CardFindRes;
import br.com.myaccounts.dto.card.CardSaveReq;
import br.com.myaccounts.dto.card.CardSaveRes;
import br.com.myaccounts.model.Bank;
import br.com.myaccounts.model.Card;
import br.com.myaccounts.model.User;
import br.com.myaccounts.service.CardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cards")
public class CardController {

    @Autowired
    private CardService service;

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
        User user = service.findUserById(req.getUserId());
        Bank bank = service.findBankById(req.getBankId());
        Card saved = service.save(req.toEntity(user, bank));
        return ResponseEntity.ok(CardSaveRes.fromEntity(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
