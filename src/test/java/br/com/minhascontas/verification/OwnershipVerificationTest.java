package br.com.minhascontas.verification;

import br.com.minhascontas.model.Cartao;
import br.com.minhascontas.model.Transacao;
import br.com.minhascontas.model.Usuario;
import br.com.minhascontas.repository.CartaoRepository;
import br.com.minhascontas.repository.TransacaoRepository;
import br.com.minhascontas.repository.UsuarioRepository;
import br.com.minhascontas.specification.TransacaoSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OwnershipVerificationTest {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void testOwnershipVerification() {
        // Create User 1
        Usuario user1 = new Usuario();
        user1.setId(UUID.randomUUID().toString());
        user1.setName("User 1");
        user1.setEmail("user1@example.com");
        usuarioRepository.save(user1);

        // Create User 2
        Usuario user2 = new Usuario();
        user2.setId(UUID.randomUUID().toString());
        user2.setName("User 2");
        user2.setEmail("user2@example.com");
        usuarioRepository.save(user2);

        // Create Card for User 1
        Cartao card1 = new Cartao();
        card1.setId(UUID.randomUUID().toString());
        card1.setUser(user1);
        cartaoRepository.save(card1);

        // Create Card for User 2
        Cartao card2 = new Cartao();
        card2.setId(UUID.randomUUID().toString());
        card2.setUser(user2);
        cartaoRepository.save(card2);

        // Create Transaction for User 1
        Transacao t1 = new Transacao();
        t1.setId(UUID.randomUUID().toString());
        t1.setCard(card1);
        t1.setDescription("User 1 Transaction");
        t1.setValue(BigDecimal.TEN);
        t1.setDate(LocalDate.now());
        transacaoRepository.save(t1);

        // Create Transaction for User 2
        Transacao t2 = new Transacao();
        t2.setId(UUID.randomUUID().toString());
        t2.setCard(card2);
        t2.setDescription("User 2 Transaction");
        t2.setValue(BigDecimal.TEN);
        t2.setDate(LocalDate.now());
        transacaoRepository.save(t2);

        // Test Filter by User 1
        Specification<Transacao> spec1 = TransacaoSpecification.filterBy(user1.getId(), null, null);
        List<Transacao> results1 = transacaoRepository.findAll(spec1);
        
        assertThat(results1).hasSize(1);
        assertThat(results1.get(0).getId()).isEqualTo(t1.getId());

        // Test Filter by User 2
        Specification<Transacao> spec2 = TransacaoSpecification.filterBy(user2.getId(), null, null);
        List<Transacao> results2 = transacaoRepository.findAll(spec2);

        assertThat(results2).hasSize(1);
        assertThat(results2.get(0).getId()).isEqualTo(t2.getId());
    }

    @Test
    public void testDateFiltering() {
        // Create User
        Usuario user = new Usuario();
        user.setId(UUID.randomUUID().toString());
        user.setName("User Date");
        user.setEmail("userdate@example.com");
        usuarioRepository.save(user);

        // Create Card
        Cartao card = new Cartao();
        card.setId(UUID.randomUUID().toString());
        card.setUser(user);
        cartaoRepository.save(card);

        // Create Transaction Previous Month
        Transacao tOld = new Transacao();
        tOld.setId(UUID.randomUUID().toString());
        tOld.setCard(card);
        tOld.setDate(LocalDate.now().minusMonths(1));
        transacaoRepository.save(tOld);

        // Create Transaction Today
        Transacao tNow = new Transacao();
        tNow.setId(UUID.randomUUID().toString());
        tNow.setCard(card);
        tNow.setDate(LocalDate.now());
        transacaoRepository.save(tNow);

        // Filter for Current Month
        LocalDate start = LocalDate.now().withDayOfMonth(1);
        LocalDate end = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());

        Specification<Transacao> spec = TransacaoSpecification.filterBy(user.getId(), start, end);
        List<Transacao> results = transacaoRepository.findAll(spec);

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getId()).isEqualTo(tNow.getId());
    }
}
