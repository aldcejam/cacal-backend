package br.com.myaccounts.controller;

import br.com.myaccounts.dto.transaction.MonthSummary;
import br.com.myaccounts.dto.transaction.TransactionFilterDto;
import br.com.myaccounts.dto.transaction.TransactionFindRes;
import br.com.myaccounts.dto.transaction.TransactionSaveReq;
import br.com.myaccounts.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public List<TransactionFindRes> getAll(TransactionFilterDto filter) {
        return transactionService.getTransactions(filter);
    }

    @GetMapping("/summary")
    public MonthSummary getSummary() {
        return transactionService.getMonthSummary();
    }

    @GetMapping("/{id}")
    public TransactionFindRes getById(@PathVariable UUID id) {
        return transactionService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionFindRes create(@RequestBody @Valid TransactionSaveReq req) {
        return transactionService.save(req);
    }

    @PutMapping("/{id}")
    public TransactionFindRes update(@PathVariable UUID id, @RequestBody @Valid TransactionSaveReq req) {
        return transactionService.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        transactionService.delete(id);
    }
}
