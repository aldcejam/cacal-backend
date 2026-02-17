package br.com.minhascontas.config;

import br.com.minhascontas.model.*;
import br.com.minhascontas.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private BancoRepository bancoRepository;
    @Autowired private CartaoRepository cartaoRepository;
    @Autowired private TransacaoRepository transacaoRepository;
    @Autowired private ReceitaRepository receitaRepository;
    @Autowired private GastoRecorrenteRepository gastoRecorrenteRepository;
    @Autowired private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            // Users
            Usuario u1 = new Usuario("u1", "João Silva", "joao@example.com", passwordEncoder.encode("123456"), "USER");
            Usuario u2 = new Usuario("u2", "Maria Santos", "maria@example.com", passwordEncoder.encode("123456"), "USER");
            usuarioRepository.saveAll(Arrays.asList(u1, u2, u3, u4));

            // Banks
            Banco b1 = new Banco(1L, "Nubank", new BigDecimal("5000"), "10", "30", "#8A05BE");
            Banco b2 = new Banco(2L, "C6 Bank", new BigDecimal("3000"), "15", "28", "#6348B8");
            Banco b3 = new Banco(3L, "Banco Inter", new BigDecimal("4000"), "12", "25", "#FF8800");
            bancoRepository.saveAll(Arrays.asList(b1, b2, b3));

            // Cards
            Cartao c1 = new Cartao("1", "1234", new BigDecimal("5000"), new BigDecimal("2660"), LocalDate.parse("2023-10-10"), LocalDate.parse("2023-10-05"), u1, b1);
            Cartao c2 = new Cartao("2", "5678", new BigDecimal("8000"), new BigDecimal("3440"), LocalDate.parse("2023-10-15"), LocalDate.parse("2023-10-10"), u2, b2);
            Cartao c3 = new Cartao("3", "9012", new BigDecimal("3500"), new BigDecimal("2220"), LocalDate.parse("2023-10-20"), LocalDate.parse("2023-10-15"), u3, b3);
            cartaoRepository.saveAll(Arrays.asList(c1, c2, c3));

            // Transactions
            Transacao t1 = new Transacao("t1", c1, "Amazon Prime", "Streaming", new BigDecimal("14.90"), "1/1", new BigDecimal("14.90"));
            Transacao t2 = new Transacao("t2", c1, "iFood", "Alimentação", new BigDecimal("45.50"), "1/1", new BigDecimal("45.50"));
            Transacao t3 = new Transacao("t3", c2, "Notebook Dell", "Eletrônicos", new BigDecimal("299.90"), "3/12", new BigDecimal("3598.80"));
            Transacao t4 = new Transacao("t4", c2, "Spotify", "Streaming", new BigDecimal("21.90"), "1/1", new BigDecimal("21.90"));
            Transacao t5 = new Transacao("t5", c3, "Uber", "Transporte", new BigDecimal("32.00"), "1/1", new BigDecimal("32.00"));
            Transacao t6 = new Transacao("t6", c3, "Farmácia", "Saúde", new BigDecimal("89.90"), "2/3", new BigDecimal("269.70"));
            Transacao t7 = new Transacao("t7", c1, "Netflix", "Streaming", new BigDecimal("55.90"), "1/1", new BigDecimal("55.90"));
            transacaoRepository.saveAll(Arrays.asList(t1, t2, t3, t4, t5, t6, t7));

            // Incomes (Receitas)
            Receita r1 = new Receita("r1", u1, "Salário Mensal", "Salário", new BigDecimal("8500.00"), 5);
            Receita r2 = new Receita("r2", u1, "Vale Refeição", "Benefício", new BigDecimal("900.00"), 1);
            Receita r3 = new Receita("r3", u1, "Rendimentos NuBank", "Investimento", new BigDecimal("150.45"), 15);
            Receita r4 = new Receita("r4", u2, "Salário", "Salário", new BigDecimal("6200.00"), 5);
            Receita r5 = new Receita("r5", u2, "Auxílio Home Office", "Benefício", new BigDecimal("200.00"), 5);
            Receita r6 = new Receita("r6", u2, "Venda de Bolos", "Extra", new BigDecimal("450.00"), 20);
            Receita r7 = new Receita("r7", u3, "Bolsa Estágio", "Salário", new BigDecimal("1200.00"), 10);
            Receita r8 = new Receita("r8", u3, "Vale Transporte", "Benefício", new BigDecimal("180.00"), 1);
            receitaRepository.saveAll(Arrays.asList(r1, r2, r3, r4, r5, r6, r7, r8));

            // Recurring Expenses
            GastoRecorrente gr1 = new GastoRecorrente("gr1", u1, "Cartão de Crédito", "Netflix", "Streaming", new BigDecimal("55.90"));
            GastoRecorrente gr2 = new GastoRecorrente("gr2", u1, "Débito Automático", "Academia SmartFit", "Saúde", new BigDecimal("89.90"));
            GastoRecorrente gr3 = new GastoRecorrente("gr3", u2, "PIX", "Spotify Premium", "Streaming", new BigDecimal("21.90"));
            GastoRecorrente gr4 = new GastoRecorrente("gr4", u2, "Cartão de Crédito", "Amazon Prime", "Streaming", new BigDecimal("14.90"));
            GastoRecorrente gr5 = new GastoRecorrente("gr5", u3, "Débito Automático", "Plano de Celular", "Telecomunicações", new BigDecimal("79.90"));
            GastoRecorrente gr6 = new GastoRecorrente("gr6", u4, "PIX", "Curso Online", "Educação", new BigDecimal("199.90"));
            gastoRecorrenteRepository.saveAll(Arrays.asList(gr1, gr2, gr3, gr4, gr5, gr6));
            
            System.out.println("Database seeded successfully with Java objects!");
        }
    }
}
