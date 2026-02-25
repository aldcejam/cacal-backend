package br.com.minhascontas.service;

import br.com.minhascontas.model.Transacao;
import br.com.minhascontas.repository.TransacaoRepository;
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
public class TransacaoServiceTest {

    @InjectMocks
    private TransacaoService transacaoService;

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    private Transacao transacao;
    private final UUID TRAN_ID = UUID.randomUUID();
    private final String EMAIL_MOCK = "test@test.com";

    @BeforeEach
    void setUp() {
        transacao = new Transacao();
        transacao.setId(TRAN_ID);
        transacao.setDescription("Test Transacao");
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

        when(transacaoRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(transacao));

        List<Transacao> result = transacaoService.findAll(start, end);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Transacao", result.get(0).getDescription());
        verify(transacaoRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    void shouldFindById() {
        mockSecurityContext();

        when(transacaoRepository.findOne(any(Specification.class))).thenReturn(Optional.of(transacao));

        Optional<Transacao> result = transacaoService.findById(TRAN_ID);

        assertTrue(result.isPresent());
        assertEquals(TRAN_ID, result.get().getId());
        verify(transacaoRepository, times(1)).findOne(any(Specification.class));
    }

    @Test
    void shouldSaveTransacao() {
        when(transacaoRepository.save(any(Transacao.class))).thenReturn(transacao);

        Transacao result = transacaoService.save(transacao);

        assertNotNull(result);
        assertEquals("Test Transacao", result.getDescription());
        verify(transacaoRepository, times(1)).save(any(Transacao.class));
    }

    @Test
    void shouldDeleteById() {
        doNothing().when(transacaoRepository).deleteById(TRAN_ID);

        transacaoService.deleteById(TRAN_ID);

        verify(transacaoRepository, times(1)).deleteById(TRAN_ID);
    }
}
