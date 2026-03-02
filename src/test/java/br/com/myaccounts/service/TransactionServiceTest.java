package br.com.myaccounts.service;

import br.com.myaccounts.model.Transaction;
import br.com.myaccounts.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

    @Nested
    @DisplayName("Method FindAll")
    class FindAll {

        @Test
        @DisplayName("Should return transactions within the period with authenticated user")
        void shouldFindTransactionsWithinPeriod() {
            // GIVEN
            mockSecurityContext();
            LocalDateTime start = LocalDateTime.now().minusDays(10);
            LocalDateTime end = LocalDateTime.now();
            when(transactionRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(transaction));

            // WHEN
            List<Transaction> result = transactionService.findAll(start, end);

            // THEN
            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals("Test Transaction", result.get(0).getDescription());
            verify(transactionRepository, times(1)).findAll(any(Specification.class));
        }
    }

    @Nested
    @DisplayName("Method FindById")
    class FindById {

        @Test
        @DisplayName("Should return transaction when it exists and user has permission")
        void shouldReturnTransaction_whenFoundAndAllowed() {
            // GIVEN
            mockSecurityContext();
            when(transactionRepository.findOne(any(Specification.class))).thenReturn(Optional.of(transaction));

            // WHEN
            Optional<Transaction> result = transactionService.findById(TRAN_ID);

            // THEN
            assertTrue(result.isPresent());
            assertEquals(TRAN_ID, result.get().getId());
            verify(transactionRepository, times(1)).findOne(any(Specification.class));
        }
    }

    @Nested
    @DisplayName("Method Save")
    class Save {

        @Test
        @DisplayName("Should save and return transaction successfully")
        void shouldSaveSuccessfully() {
            // GIVEN
            when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

            // WHEN
            Transaction result = transactionService.save(transaction);

            // THEN
            assertNotNull(result);
            assertEquals("Test Transaction", result.getDescription());
            verify(transactionRepository, times(1)).save(any(Transaction.class));
        }
    }

    @Nested
    @DisplayName("Method DeleteById")
    class DeleteById {

        @Test
        @DisplayName("Should delete the transaction by ID")
        void shouldDeleteSuccessfully() {
            // GIVEN
            doNothing().when(transactionRepository).deleteById(TRAN_ID);

            // WHEN
            transactionService.deleteById(TRAN_ID);

            // THEN
            verify(transactionRepository, times(1)).deleteById(TRAN_ID);
        }
    }
}
