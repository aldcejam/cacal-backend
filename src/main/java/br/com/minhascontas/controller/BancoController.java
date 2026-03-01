package br.com.minhascontas.controller;

import br.com.minhascontas.dto.bank.BankFindRes;
import br.com.minhascontas.dto.bank.BankSaveReq;
import br.com.minhascontas.dto.bank.BankSaveRes;
import br.com.minhascontas.model.Banco;
import br.com.minhascontas.service.BancoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bancos")
public class BancoController {

    @Autowired
    private BancoService service;

    @GetMapping
    public List<BankFindRes> findAll() {
        return service.findAll().stream()
                .map(BankFindRes::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankFindRes> findById(@PathVariable UUID id) {
        return service.findById(id)
                .map(BankFindRes::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BankSaveRes> save(@Valid @RequestBody BankSaveReq req) {
        Banco saved = service.save(req.toEntity());
        return ResponseEntity.ok(BankSaveRes.fromEntity(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
