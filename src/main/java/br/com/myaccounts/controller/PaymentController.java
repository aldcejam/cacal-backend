package br.com.myaccounts.controller;

import br.com.myaccounts.dto.payment.PaymentFindRes;
import br.com.myaccounts.dto.payment.PaymentSaveReq;
import br.com.myaccounts.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentFindRes save(@RequestBody @Valid PaymentSaveReq req) {
        return paymentService.save(req);
    }

    @GetMapping
    public List<PaymentFindRes> findAll() {
        return paymentService.findAll();
    }

    @GetMapping("/{id}")
    public PaymentFindRes findById(@PathVariable UUID id) {
        return paymentService.findById(id);
    }

    @PutMapping("/{id}")
    public PaymentFindRes update(@PathVariable UUID id, @RequestBody @Valid PaymentSaveReq req) {
        return paymentService.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        paymentService.delete(id);
    }
}
