package br.com.minhascontas.controller;

import br.com.minhascontas.IntegrationTest;
import br.com.minhascontas.model.Transacao;
import br.com.minhascontas.repository.TransacaoRepository;
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

public class TransacaoControllerIT extends IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransacaoRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    @WithMockUser(username = "test@minhascontas.com")
    void shouldFindEmptyTransacaoList() throws Exception {
        mockMvc.perform(get("/transacoes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @WithMockUser(username = "test@minhascontas.com")
    void shouldCreateTransacao() throws Exception {
        Transacao payload = new Transacao();
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
