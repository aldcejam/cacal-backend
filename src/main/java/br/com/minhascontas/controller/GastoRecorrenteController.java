package br.com.minhascontas.controller;

import br.com.minhascontas.dto.expense.ExpenseFindRes;
import br.com.minhascontas.dto.expense.ExpenseSaveReq;
import br.com.minhascontas.dto.expense.ExpenseSaveRes;
import br.com.minhascontas.model.GastoRecorrente;
import br.com.minhascontas.model.Usuario;
import br.com.minhascontas.service.GastoRecorrenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/gastosRecorrentes")
public class GastoRecorrenteController {

    @Autowired
    private GastoRecorrenteService service;

    @GetMapping
    public ResponseEntity<List<ExpenseFindRes>> findAll(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<ExpenseFindRes> result = service.findAll(start, end).stream()
                .map(ExpenseFindRes::fromEntity)
                .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseFindRes> findById(@PathVariable UUID id) {
        return service.findById(id)
                .map(ExpenseFindRes::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ExpenseSaveRes> save(@Valid @RequestBody ExpenseSaveReq req) {
        Usuario user = service.findUsuarioById(req.getUserId());
        GastoRecorrente saved = service.save(req.toEntity(user));
        return ResponseEntity.ok(ExpenseSaveRes.fromEntity(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
