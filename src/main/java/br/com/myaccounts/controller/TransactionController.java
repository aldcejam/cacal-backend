package br.com.myaccounts.controller;

import br.com.myaccounts.dto.transaction.TransactionFindRes;
import br.com.myaccounts.dto.transaction.TransactionSaveReq;
import br.com.myaccounts.dto.transaction.TransactionSaveRes;
import br.com.myaccounts.model.Card;
import br.com.myaccounts.model.Transaction;
import br.com.myaccounts.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @GetMapping
    public ResponseEntity<List<TransactionFindRes>> findAll(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<TransactionFindRes> result = service.findAll(start, end).stream()
                .map(TransactionFindRes::fromEntity)
                .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionFindRes> findById(@PathVariable UUID id) {
        return service.findById(id)
                .map(TransactionFindRes::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TransactionSaveRes> save(@Valid @RequestBody TransactionSaveReq req) {
        Card card = service.findCardById(req.getCardId());
        Transaction saved = service.save(req.toEntity(card));
        return ResponseEntity.ok(TransactionSaveRes.fromEntity(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
