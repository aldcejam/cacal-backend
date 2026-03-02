package br.com.myaccounts.controller;

import br.com.myaccounts.IntegrationTest;
import br.com.myaccounts.dto.transaction.TransactionSaveReq;
import br.com.myaccounts.model.Bank;
import br.com.myaccounts.model.Card;
import br.com.myaccounts.model.User;
import br.com.myaccounts.repository.BankRepository;
import br.com.myaccounts.repository.CardRepository;
import br.com.myaccounts.repository.TransactionRepository;
import br.com.myaccounts.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TransactionControllerIT extends IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private br.com.myaccounts.repository.IncomeRepository incomeRepository;

    @Autowired
    private br.com.myaccounts.repository.RecurringExpenseRepository recurringExpenseRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Card card;

    @BeforeEach
    void setup() {
        transactionRepository.deleteAll();
        cardRepository.deleteAll();
        incomeRepository.deleteAll();
        recurringExpenseRepository.deleteAll();
        userRepository.deleteAll();
        bankRepository.deleteAll();

        // Setup User, Bank, and Card
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName("Tester");
        user.setEmail("test@minhascontas.com");
        user.setPassword("secret");
        user.setRole("USER");
        userRepository.save(user);

        Bank bank = new Bank();
        bank.setId(UUID.randomUUID());
        bank.setName("Bank Test");
        bank.setColor("#000");
        bank.setLimitValue(new BigDecimal("1000"));
        bank.setClosingDate("10");
        bank.setDueDate("20");
        bankRepository.save(bank);

        card = new Card();
        card.setId(UUID.randomUUID());
        card.setUser(user);
        card.setBank(bank);
        card.setLastDigits("1234");
        card.setLimitValue(new BigDecimal("1000"));
        card.setAvailable(new BigDecimal("1000"));
        card.setDueDate(LocalDate.now());
        card.setClosingDate(LocalDate.now());
        cardRepository.save(card);
    }

    @Nested
    @DisplayName("GET /transactions Requests")
    class GetTransacoes {

        @Test
        @DisplayName("Should fetch and return an empty list of transactions successfully")
        @WithMockUser(username = "test@minhascontas.com")
        void shouldFindEmptyTransactionList() throws Exception {
            // GIVEN (setup scenario in BeforeEach with deleteAll)

            // WHEN & THEN
            mockMvc.perform(get("/transactions")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$").isEmpty());
        }
    }

    @Nested
    @DisplayName("POST /transactions Requests")
    class PostTransacoes {

        @Test
        @DisplayName("Should create a new transaction successfully")
        @WithMockUser(username = "test@minhascontas.com")
        void shouldCreateNewTransaction() throws Exception {
            // GIVEN
            TransactionSaveReq payload = new TransactionSaveReq();
            payload.setCardId(card.getId());
            payload.setDescription("Compra Teste IT");
            payload.setValue(new BigDecimal("150.50"));
            payload.setTotal(new BigDecimal("150.50"));
            payload.setCategory("Alimentacao");
            payload.setParcels("1/1");

            // WHEN & THEN
            mockMvc.perform(post("/transactions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(payload)))
                    .andDo(org.springframework.test.web.servlet.result.MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.description").value("Compra Teste IT"))
                    .andExpect(jsonPath("$.value").value(150.5));
        }
    }
}
