package br.com.myaccounts.controller;

import br.com.myaccounts.IntegrationTest;
import br.com.myaccounts.model.Transaction;
import br.com.myaccounts.repository.TransactionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TransactionControllerIT extends IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    @WithMockUser(username = "test@minhascontas.com")
    void shouldFindEmptyTransactionList() throws Exception {
        mockMvc.perform(get("/transacoes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @WithMockUser(username = "test@minhascontas.com")
    void shouldCreateTransaction() throws Exception {
        Transaction payload = new Transaction();
        payload.setDescription("Compra Teste IT");
        payload.setValue(new BigDecimal("150.50"));
        payload.setTotal(new BigDecimal("150.50"));
        payload.setCategory("Alimentacao");

        mockMvc.perform(post("/transacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.description").value("Compra Teste IT"))
                .andExpect(jsonPath("$.value").value(150.5));
    }
}
