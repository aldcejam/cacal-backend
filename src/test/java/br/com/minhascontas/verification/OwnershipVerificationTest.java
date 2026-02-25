package br.com.minhascontas.verification;

import br.com.minhascontas.IntegrationTest;
import br.com.minhascontas.model.Cartao;
import br.com.minhascontas.model.Transacao;
import br.com.minhascontas.model.Usuario;
import br.com.minhascontas.repository.CartaoRepository;
import br.com.minhascontas.repository.TransacaoRepository;
import br.com.minhascontas.repository.UsuarioRepository;
import br.com.minhascontas.specification.EntitySpecification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
public class OwnershipVerificationTest extends IntegrationTest {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testOwnershipVerification() {
        // Create User 1
        Usuario user1 = new Usuario();
        user1.setId(UUID.randomUUID());
        user1.setName("User 1");
        user1.setEmail("user1@example.com");
        usuarioRepository.save(user1);

        // Create User 2
        Usuario user2 = new Usuario();
        user2.setId(UUID.randomUUID());
        user2.setName("User 2");
        user2.setEmail("user2@example.com");
        usuarioRepository.save(user2);

        // Create Card for User 1
        Cartao card1 = new Cartao();
        card1.setId(UUID.randomUUID());
        card1.setUser(user1);
        cartaoRepository.save(card1);

        // Create Card for User 2
        Cartao card2 = new Cartao();
        card2.setId(UUID.randomUUID());
        card2.setUser(user2);
        cartaoRepository.save(card2);

        // Create Transaction for User 1
        Transacao t1 = new Transacao();
        t1.setId(UUID.randomUUID());
        t1.setCard(card1);
        t1.setDescription("User 1 Transaction");
        t1.setValue(BigDecimal.TEN);
        transacaoRepository.saveAndFlush(t1);

        // Create Transaction for User 2
        Transacao t2 = new Transacao();
        t2.setId(UUID.randomUUID());
        t2.setCard(card2);
        t2.setDescription("User 2 Transaction");
        t2.setValue(BigDecimal.TEN);
        transacaoRepository.saveAndFlush(t2);

        // Test Filter by User 1
        Specification<Transacao> spec1 = EntitySpecification.filterByUser(user1.getEmail());
        List<Transacao> results1 = transacaoRepository.findAll(spec1);
        
        assertThat(results1).hasSize(1);
        assertThat(results1.get(0).getId()).isEqualTo(t1.getId());

        // Test Filter by User 2
        Specification<Transacao> spec2 = EntitySpecification.filterByUser(user2.getEmail());
        List<Transacao> results2 = transacaoRepository.findAll(spec2);

        assertThat(results2).hasSize(1);
        assertThat(results2.get(0).getId()).isEqualTo(t2.getId());
    }

    @Test
    public void testDateFiltering() {
        // Create User
        Usuario user = new Usuario();
        user.setId(UUID.randomUUID());
        user.setName("User Date");
        user.setEmail("userdate@example.com");
        usuarioRepository.save(user);

        // Create Card
        Cartao card = new Cartao();
        card.setId(UUID.randomUUID());
        card.setUser(user);
        cartaoRepository.save(card);

        // Create Transaction Previous Month
        Transacao tOld = new Transacao();
        tOld.setId(UUID.randomUUID());
        tOld.setCard(card);
        transacaoRepository.saveAndFlush(tOld);
        
        // Force the created_at to be in the past
        LocalDateTime pastDate = LocalDateTime.now().minusMonths(1);
        jdbcTemplate.update("UPDATE transacoes SET created_at = ? WHERE id = ?", pastDate, tOld.getId());

        // Create Transaction Today
        Transacao tNow = new Transacao();
        tNow.setId(UUID.randomUUID());
        tNow.setCard(card);
        transacaoRepository.saveAndFlush(tNow);

        // Filter for Current Month
        LocalDateTime start = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime end = start.plusMonths(1).minusNanos(1);

        Specification<Transacao> spec = Specification.where(EntitySpecification.<Transacao>filterByUser(user.getEmail()))
                .and(EntitySpecification.filterByDateRange(start, end));

        List<Transacao> results = transacaoRepository.findAll(spec);

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getId()).isEqualTo(tNow.getId());
    }
}
