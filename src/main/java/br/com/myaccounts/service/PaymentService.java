package br.com.myaccounts.service;

import br.com.myaccounts.dto.payment.PaymentFindRes;
import br.com.myaccounts.dto.payment.PaymentSaveReq;
import br.com.myaccounts.model.Bank;
import br.com.myaccounts.model.Payment;
import br.com.myaccounts.model.PaymentRelation;
import br.com.myaccounts.model.User;
import br.com.myaccounts.repository.BankRepository;
import br.com.myaccounts.repository.PaymentRelationRepository;
import br.com.myaccounts.repository.PaymentRepository;
import br.com.myaccounts.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentRelationRepository paymentRelationRepository;
    private final UserRepository userRepository;
    private final BankRepository bankRepository;

    private User getAuthenticatedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @Transactional
    public PaymentFindRes save(PaymentSaveReq req) {
        User user = getAuthenticatedUser();

        Payment payment = new Payment();
        payment.setName(req.getName());
        payment.setType(req.getType());
        payment.setOwnerUser(user);
        payment.setDetails(req.getDetails());

        payment = paymentRepository.save(payment);

        if (req.getBankId() != null || req.getPersonName() != null) {
            PaymentRelation relation = new PaymentRelation();
            relation.setPayment(payment);

            if (req.getBankId() != null && req.getPersonName() != null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Payment cannot be linked to both a bank and a person");
            }

            if (req.getBankId() != null) {
                Bank bank = bankRepository.findById(req.getBankId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bank not found"));
                relation.setBank(bank);
            } else {
                relation.setPersonName(req.getPersonName());
            }

            relation = paymentRelationRepository.save(relation);
            payment.setRelation(relation);
        }

        return toDto(payment);
    }

    @Transactional
    public PaymentFindRes update(UUID id, PaymentSaveReq req) {
        User user = getAuthenticatedUser();
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found"));

        if (!payment.getOwnerUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        payment.setName(req.getName());
        payment.setType(req.getType());
        payment.setDetails(req.getDetails());

        if (req.getBankId() != null || req.getPersonName() != null) {
            PaymentRelation relation = payment.getRelation();
            if (relation == null) {
                relation = new PaymentRelation();
                relation.setPayment(payment);
            }

            if (req.getBankId() != null && req.getPersonName() != null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Payment cannot be linked to both a bank and a person");
            }

            if (req.getBankId() != null) {
                Bank bank = bankRepository.findById(req.getBankId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bank not found"));
                relation.setBank(bank);
                relation.setPersonName(null);
            } else {
                relation.setPersonName(req.getPersonName());
                relation.setBank(null);
            }
            paymentRelationRepository.save(relation);
            payment.setRelation(relation);
        } else if (payment.getRelation() != null) {
            paymentRelationRepository.delete(payment.getRelation());
            payment.setRelation(null);
        }

        return toDto(payment);
    }

    public List<PaymentFindRes> findAll() {
        User user = getAuthenticatedUser();
        return paymentRepository.findByOwnerUser(user).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public PaymentFindRes findById(UUID id) {
        User user = getAuthenticatedUser();
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found"));

        if (!payment.getOwnerUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        return toDto(payment);
    }

    @Transactional
    public void delete(UUID id) {
        User user = getAuthenticatedUser();
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found"));

        if (!payment.getOwnerUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        paymentRepository.delete(payment);
    }

    private PaymentFindRes toDto(Payment payment) {
        PaymentFindRes dto = new PaymentFindRes();
        dto.setId(payment.getId());
        dto.setName(payment.getName());
        dto.setType(payment.getType());
        dto.setDetails(payment.getDetails());

        if (payment.getRelation() != null) {
            if (payment.getRelation().getBank() != null) {
                dto.setBankId(payment.getRelation().getBank().getId());
            }
            dto.setPersonName(payment.getRelation().getPersonName());
        }

        return dto;
    }
}
