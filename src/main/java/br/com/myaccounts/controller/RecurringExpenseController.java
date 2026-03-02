package br.com.myaccounts.controller;

import br.com.myaccounts.dto.expense.ExpenseFindRes;
import br.com.myaccounts.dto.expense.ExpenseSaveReq;
import br.com.myaccounts.dto.expense.ExpenseSaveRes;
import br.com.myaccounts.model.RecurringExpense;
import br.com.myaccounts.model.User;
import br.com.myaccounts.service.RecurringExpenseService;
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
public class RecurringExpenseController {

    @Autowired
    private RecurringExpenseService service;

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
        User user = service.findUserById(req.getUserId());
        RecurringExpense saved = service.save(req.toEntity(user));
        return ResponseEntity.ok(ExpenseSaveRes.fromEntity(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
