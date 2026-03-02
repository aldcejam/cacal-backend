package br.com.myaccounts.service;

import br.com.myaccounts.model.Transaction;
import br.com.myaccounts.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    private Transaction transaction;
    private final UUID TRAN_ID = UUID.randomUUID();
    private final String EMAIL_MOCK = "test@test.com";

    @BeforeEach
    void setUp() {
        transaction = new Transaction();
        transaction.setId(TRAN_ID);
        transaction.setDescription("Test Transaction");
    }

    private void mockSecurityContext() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(EMAIL_MOCK);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void shouldFindAllWithDateRange() {
        mockSecurityContext();
        LocalDateTime start = LocalDateTime.now().minusDays(10);
        LocalDateTime end = LocalDateTime.now();

        when(transactionRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(transaction));

        List<Transaction> result = transactionService.findAll(start, end);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Transaction", result.get(0).getDescription());
        verify(transactionRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    void shouldFindById() {
        mockSecurityContext();

        when(transactionRepository.findOne(any(Specification.class))).thenReturn(Optional.of(transaction));

        Optional<Transaction> result = transactionService.findById(TRAN_ID);

        assertTrue(result.isPresent());
        assertEquals(TRAN_ID, result.get().getId());
        verify(transactionRepository, times(1)).findOne(any(Specification.class));
    }

    @Test
    void shouldSaveTransaction() {
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction result = transactionService.save(transaction);

        assertNotNull(result);
        assertEquals("Test Transaction", result.getDescription());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void shouldDeleteById() {
        doNothing().when(transactionRepository).deleteById(TRAN_ID);

        transactionService.deleteById(TRAN_ID);

        verify(transactionRepository, times(1)).deleteById(TRAN_ID);
    }
}
