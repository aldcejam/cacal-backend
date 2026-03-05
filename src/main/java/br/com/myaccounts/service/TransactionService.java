package br.com.myaccounts.service;

import br.com.myaccounts.dto.transaction.MonthSummary;
import br.com.myaccounts.dto.transaction.TransactionFilterDto;
import br.com.myaccounts.dto.transaction.TransactionFindRes;
import br.com.myaccounts.dto.transaction.TransactionSaveReq;
import br.com.myaccounts.dto.auth.UserRes;
import br.com.myaccounts.model.Payment;
import br.com.myaccounts.model.Transaction;
import br.com.myaccounts.model.User;
import br.com.myaccounts.model.enums.TransactionType;
import br.com.myaccounts.repository.PaymentRepository;
import br.com.myaccounts.repository.TransactionRepository;
import br.com.myaccounts.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    private User getAuthenticatedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public List<TransactionFindRes> getTransactions(TransactionFilterDto filter) {
        User user = getAuthenticatedUser();
        LocalDate start = filter.getStartDate();
        LocalDate end = filter.getEndDate();

        List<Transaction> transactions;
        if (start != null && end != null) {
            transactions = transactionRepository.findFilteredTransactions(user, start, end, filter.getType());
        } else {
            transactions = transactionRepository.findByUser(user);
            if (filter.getType() != null) {
                transactions = transactions.stream().filter(t -> t.getType() == filter.getType())
                        .collect(Collectors.toList());
            }
        }
        return transactions.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional
    public TransactionFindRes save(TransactionSaveReq req) {
        User user = getAuthenticatedUser();
        Transaction transaction = new Transaction();

        transaction.setUser(user);
        transaction.setType(req.getType());
        transaction.setDescription(req.getDescription());
        transaction.setCategory(req.getCategory());
        transaction.setValue(req.getValue());
        transaction.setIsPaid(req.getIsPaid());
        transaction.setRecurrenceDetails(req.getRecurrenceDetails());

        if (req.getPaymentId() != null) {
            Payment payment = paymentRepository.findById(req.getPaymentId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found"));

            if (!payment.getOwnerUser().getId().equals(user.getId())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
            }
            transaction.setPayment(payment);
        }

        transaction = transactionRepository.save(transaction);
        return toDto(transaction);
    }

    @Transactional
    public TransactionFindRes update(UUID id, TransactionSaveReq req) {
        User user = getAuthenticatedUser();
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found"));

        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        transaction.setType(req.getType());
        transaction.setDescription(req.getDescription());
        transaction.setCategory(req.getCategory());
        transaction.setValue(req.getValue());
        transaction.setIsPaid(req.getIsPaid());
        transaction.setRecurrenceDetails(req.getRecurrenceDetails());

        if (req.getPaymentId() != null) {
            if (transaction.getPayment() == null || !transaction.getPayment().getId().equals(req.getPaymentId())) {
                Payment payment = paymentRepository.findById(req.getPaymentId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found"));
                if (!payment.getOwnerUser().getId().equals(user.getId())) {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
                }
                transaction.setPayment(payment);
            }
        } else {
            transaction.setPayment(null);
        }

        transaction = transactionRepository.save(transaction);
        return toDto(transaction);
    }

    public TransactionFindRes findById(UUID id) {
        User user = getAuthenticatedUser();
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found"));

        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        return toDto(transaction);
    }

    @Transactional
    public void delete(UUID id) {
        User user = getAuthenticatedUser();
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found"));

        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }
        transactionRepository.delete(transaction);
    }

    public MonthSummary getMonthSummary() {
        User user = getAuthenticatedUser();
        int startDay = user.getViewingIntervalStartDay() != null ? user.getViewingIntervalStartDay() : 1;

        LocalDate now = LocalDate.now();
        LocalDate startDate;
        LocalDate endDate;

        if (now.getDayOfMonth() >= startDay) {
            startDate = now.withDayOfMonth(startDay);
            endDate = now.plusMonths(1).withDayOfMonth(startDay).minusDays(1);
        } else {
            startDate = now.minusMonths(1).withDayOfMonth(startDay);
            endDate = now.withDayOfMonth(startDay).minusDays(1);
        }

        List<Transaction> transactionsInPeriod = transactionRepository.findFilteredTransactions(user, startDate,
                endDate, null);

        BigDecimal pendingToPay = BigDecimal.ZERO;
        BigDecimal incomes = BigDecimal.ZERO;

        for (Transaction t : transactionsInPeriod) {
            if (t.getType() == TransactionType.EXPENSE && !Boolean.TRUE.equals(t.getIsPaid())) {
                pendingToPay = pendingToPay.add(t.getValue());
            }
            if (t.getType() == TransactionType.INCOME) {
                incomes = incomes.add(t.getValue());
            }
        }

        MonthSummary summary = new MonthSummary();
        summary.setPendingToPay(pendingToPay);
        summary.setLeftover(incomes.subtract(pendingToPay));
        summary.setPeriodTransactions(transactionsInPeriod.stream().map(this::toDto).collect(Collectors.toList()));

        return summary;
    }

    private TransactionFindRes toDto(Transaction transaction) {
        TransactionFindRes dto = new TransactionFindRes();
        dto.setId(transaction.getId());
        dto.setType(transaction.getType());
        dto.setDescription(transaction.getDescription());
        dto.setCategory(transaction.getCategory());
        dto.setValue(transaction.getValue());
        dto.setIsPaid(transaction.getIsPaid());
        dto.setRecurrenceDetails(transaction.getRecurrenceDetails());

        if (transaction.getPayment() != null) {
            dto.setPaymentId(transaction.getPayment().getId());
            dto.setPaymentName(transaction.getPayment().getName());
        }

        if (transaction.getUser() != null) {
            dto.setUser(UserRes.fromEntity(transaction.getUser()));
        }

        return dto;
    }
}
