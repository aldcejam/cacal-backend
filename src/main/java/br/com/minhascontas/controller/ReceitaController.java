package br.com.minhascontas.controller;

import br.com.minhascontas.dto.income.IncomeFindRes;
import br.com.minhascontas.dto.income.IncomeSaveReq;
import br.com.minhascontas.dto.income.IncomeSaveRes;
import br.com.minhascontas.model.Receita;
import br.com.minhascontas.model.Usuario;
import br.com.minhascontas.service.ReceitaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    @Autowired
    private ReceitaService service;

    @GetMapping
    public ResponseEntity<List<IncomeFindRes>> findAll(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<IncomeFindRes> result = service.findAll(start, end).stream()
                .map(IncomeFindRes::fromEntity)
                .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncomeFindRes> findById(@PathVariable UUID id) {
        return service.findById(id)
                .map(IncomeFindRes::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<IncomeSaveRes> save(@Valid @RequestBody IncomeSaveReq req) {
        Usuario user = service.findUsuarioById(req.getUserId());
        Receita saved = service.save(req.toEntity(user));
        return ResponseEntity.ok(IncomeSaveRes.fromEntity(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
