package br.com.myaccounts.service;

import br.com.myaccounts.dto.transaction.MonthSummary;
import br.com.myaccounts.model.Transaction;
import br.com.myaccounts.model.User;
import br.com.myaccounts.model.enums.TransactionType;
import br.com.myaccounts.repository.TransactionRepository;
import br.com.myaccounts.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TransactionService transactionService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(UUID.randomUUID());
        mockUser.setEmail("test@test.com");
        mockUser.setViewingIntervalStartDay(10); // user custom day

        Authentication auth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        when(auth.getName()).thenReturn("test@test.com");
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void getMonthSummary_shouldCalculatePendingAndLeftoverCorrectly() {
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(mockUser));

        Transaction t1 = new Transaction();
        t1.setType(TransactionType.INCOME);
        t1.setValue(new BigDecimal("5000.00"));

        Transaction t2 = new Transaction();
        t2.setType(TransactionType.EXPENSE);
        t2.setValue(new BigDecimal("1000.00"));
        t2.setIsPaid(false); // pending

        Transaction t3 = new Transaction();
        t3.setType(TransactionType.EXPENSE);
        t3.setValue(new BigDecimal("500.00"));
        t3.setIsPaid(true); // already paid, should not count towards pending

        when(transactionRepository.findFilteredTransactions(eq(mockUser), any(LocalDate.class), any(LocalDate.class),
                eq(null)))
                .thenReturn(Arrays.asList(t1, t2, t3));

        MonthSummary summary = transactionService.getMonthSummary();

        // Leftover calculation: Income (5000) - Pending to Pay (1000) = 4000
        assertEquals(new BigDecimal("1000.00"), summary.getPendingToPay());
        assertEquals(new BigDecimal("4000.00"), summary.getLeftover());
        assertEquals(3, summary.getPeriodTransactions().size());
    }
}
