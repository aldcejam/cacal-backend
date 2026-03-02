package br.com.myaccounts.verification;

import br.com.myaccounts.IntegrationTest;
import br.com.myaccounts.model.Bank;
import br.com.myaccounts.model.Card;
import br.com.myaccounts.model.Transaction;
import br.com.myaccounts.model.User;
import br.com.myaccounts.repository.BankRepository;
import br.com.myaccounts.repository.CardRepository;
import br.com.myaccounts.repository.TransactionRepository;
import br.com.myaccounts.repository.UserRepository;
import br.com.myaccounts.specification.EntitySpecification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
public class OwnershipVerificationTest extends IntegrationTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Bank createTestBank(String name) {
        Bank bank = new Bank();
        bank.setId(UUID.randomUUID());
        bank.setName(name);
        bank.setLimitValue(BigDecimal.valueOf(10000));
        bank.setDueDate("10");
        bank.setClosingDate("5");
        bank.setColor("#000");
        return bankRepository.save(bank);
    }

    @Test
    public void testOwnershipVerification() {
        // Create User 1
        User user1 = new User();
        user1.setId(UUID.randomUUID());
        user1.setName("User 1");
        user1.setEmail("user1@example.com");
        user1.setPassword("hashed_password");
        user1.setRole("USER");
        userRepository.save(user1);

        // Create User 2
        User user2 = new User();
        user2.setId(UUID.randomUUID());
        user2.setName("User 2");
        user2.setEmail("user2@example.com");
        user2.setPassword("hashed_password");
        user2.setRole("USER");
        userRepository.save(user2);

        // Create Card for User 1
        Bank bank1 = createTestBank("Bank Teste 1");
        Card card1 = new Card();
        card1.setId(UUID.randomUUID());
        card1.setUser(user1);
        card1.setLastDigits("1234");
        card1.setLimitValue(BigDecimal.valueOf(1000));
        card1.setAvailable(BigDecimal.valueOf(1000));
        card1.setDueDate(LocalDate.now());
        card1.setClosingDate(LocalDate.now());
        card1.setBank(bank1);
        cardRepository.save(card1);

        // Create Card for User 2
        Bank bank2 = createTestBank("Bank Teste 2");
        Card card2 = new Card();
        card2.setId(UUID.randomUUID());
        card2.setUser(user2);
        card2.setLastDigits("5678");
        card2.setLimitValue(BigDecimal.valueOf(2000));
        card2.setAvailable(BigDecimal.valueOf(2000));
        card2.setDueDate(LocalDate.now());
        card2.setClosingDate(LocalDate.now());
        card2.setBank(bank2);
        cardRepository.save(card2);

        // Create Transaction for User 1
        Transaction t1 = new Transaction();
        t1.setId(UUID.randomUUID());
        t1.setCard(card1);
        t1.setDescription("User 1 Transaction");
        t1.setCategory("Test");
        t1.setValue(BigDecimal.TEN);
        t1.setParcels("1/1");
        t1.setTotal(BigDecimal.TEN);
        transactionRepository.saveAndFlush(t1);

        // Create Transaction for User 2
        Transaction t2 = new Transaction();
        t2.setId(UUID.randomUUID());
        t2.setCard(card2);
        t2.setDescription("User 2 Transaction");
        t2.setCategory("Test");
        t2.setValue(BigDecimal.TEN);
        t2.setParcels("1/1");
        t2.setTotal(BigDecimal.TEN);
        transactionRepository.saveAndFlush(t2);

        // Test Filter by User 1
        Specification<Transaction> spec1 = EntitySpecification.filterByUser(user1.getEmail());
        List<Transaction> results1 = transactionRepository.findAll(spec1);

        assertThat(results1).hasSize(1);
        assertThat(results1.get(0).getId()).isEqualTo(t1.getId());

        // Test Filter by User 2
        Specification<Transaction> spec2 = EntitySpecification.filterByUser(user2.getEmail());
        List<Transaction> results2 = transactionRepository.findAll(spec2);

        assertThat(results2).hasSize(1);
        assertThat(results2.get(0).getId()).isEqualTo(t2.getId());
    }

    @Test
    public void testDateFiltering() {
        // Create User
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName("User Date");
        user.setEmail("userdate@example.com");
        user.setPassword("hashed_password");
        user.setRole("USER");
        userRepository.save(user);

        // Create Card
        Bank bank = createTestBank("Bank Teste Date");
        Card card = new Card();
        card.setId(UUID.randomUUID());
        card.setUser(user);
        card.setLastDigits("9999");
        card.setLimitValue(BigDecimal.valueOf(5000));
        card.setAvailable(BigDecimal.valueOf(5000));
        card.setDueDate(LocalDate.now());
        card.setClosingDate(LocalDate.now());
        card.setBank(bank);
        cardRepository.save(card);

        // Create Transaction Previous Month
        Transaction tOld = new Transaction();
        tOld.setId(UUID.randomUUID());
        tOld.setCard(card);
        tOld.setDescription("Old");
        tOld.setCategory("Test");
        tOld.setValue(BigDecimal.ONE);
        tOld.setParcels("1/1");
        tOld.setTotal(BigDecimal.ONE);
        transactionRepository.saveAndFlush(tOld);

        // Force the created_at to be in the past
        LocalDateTime pastDate = LocalDateTime.now().minusMonths(1);
        jdbcTemplate.update("UPDATE transacoes SET created_at = ? WHERE id = ?", pastDate, tOld.getId());

        // Create Transaction Today
        Transaction tNow = new Transaction();
        tNow.setId(UUID.randomUUID());
        tNow.setCard(card);
        tNow.setDescription("Now");
        tNow.setCategory("Test");
        tNow.setValue(BigDecimal.ONE);
        tNow.setParcels("1/1");
        tNow.setTotal(BigDecimal.ONE);
        transactionRepository.saveAndFlush(tNow);

        // Filter for Current Month
        LocalDateTime start = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime end = start.plusMonths(1).minusNanos(1);

        Specification<Transaction> spec = Specification
                .where(EntitySpecification.<Transaction>filterByUser(user.getEmail()))
                .and(EntitySpecification.filterByDateRange(start, end));

        List<Transaction> results = transactionRepository.findAll(spec);

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getId()).isEqualTo(tNow.getId());
    }
}
