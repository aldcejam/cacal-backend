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
import java.util.UUID;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private BancoRepository bancoRepository;
    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private TransacaoRepository transacaoRepository;
    @Autowired
    private ReceitaRepository receitaRepository;
    @Autowired
    private GastoRecorrenteRepository gastoRecorrenteRepository;
    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            // Users
            Usuario u1 = new Usuario();
            u1.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
            u1.setName("João Silva");
            u1.setEmail("joao@example.com");
            u1.setPassword(passwordEncoder.encode("123456"));
            u1.setRole("USER");

            Usuario u2 = new Usuario();
            u2.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174001"));
            u2.setName("Maria Santos");
            u2.setEmail("maria@example.com");
            u2.setPassword(passwordEncoder.encode("123456"));
            u2.setRole("USER");
            usuarioRepository.saveAll(Arrays.asList(u1, u2));

            // Banks
            Banco b1 = new Banco("Nubank", new BigDecimal("5000"), "10", "30", "#8A05BE");
            b1.setId(UUID.fromString("223e4567-e89b-12d3-a456-426614174000"));

            Banco b2 = new Banco("C6 Bank", new BigDecimal("3000"), "15", "28", "#6348B8");
            b2.setId(UUID.fromString("223e4567-e89b-12d3-a456-426614174001"));

            Banco b3 = new Banco("Banco Inter", new BigDecimal("4000"), "12", "25", "#FF8800");
            b3.setId(UUID.fromString("223e4567-e89b-12d3-a456-426614174002"));
            bancoRepository.saveAll(Arrays.asList(b1, b2, b3));

            // Cards
            Cartao c1 = new Cartao("1234", new BigDecimal("5000"), new BigDecimal("2660"),
                    LocalDate.parse("2023-10-10"), LocalDate.parse("2023-10-05"), u1, b1);
            c1.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174000"));

            Cartao c2 = new Cartao("5678", new BigDecimal("8000"), new BigDecimal("3440"),
                    LocalDate.parse("2023-10-15"), LocalDate.parse("2023-10-10"), u2, b2);
            c2.setId(UUID.fromString("323e4567-e89b-12d3-a456-426614174001"));
            cartaoRepository.saveAll(Arrays.asList(c1, c2));

            // Transactions
            Transacao t1 = new Transacao(c1, "Amazon Prime", "Streaming", new BigDecimal("14.90"), "1/1",
                    new BigDecimal("14.90"));
            t1.setId(UUID.fromString("423e4567-e89b-12d3-a456-426614174000"));

            Transacao t2 = new Transacao(c1, "iFood", "Alimentação", new BigDecimal("45.50"), "1/1",
                    new BigDecimal("45.50"));
            t2.setId(UUID.fromString("423e4567-e89b-12d3-a456-426614174001"));

            Transacao t3 = new Transacao(c2, "Notebook Dell", "Eletrônicos", new BigDecimal("299.90"), "3/12",
                    new BigDecimal("3598.80"));
            t3.setId(UUID.fromString("423e4567-e89b-12d3-a456-426614174002"));

            Transacao t4 = new Transacao(c2, "Spotify", "Streaming", new BigDecimal("21.90"), "1/1",
                    new BigDecimal("21.90"));
            t4.setId(UUID.fromString("423e4567-e89b-12d3-a456-426614174003"));

            Transacao t7 = new Transacao(c1, "Netflix", "Streaming", new BigDecimal("55.90"), "1/1",
                    new BigDecimal("55.90"));
            t7.setId(UUID.fromString("423e4567-e89b-12d3-a456-426614174004"));
            transacaoRepository.saveAll(Arrays.asList(t1, t2, t3, t4, t7));

            // Incomes (Receitas)
            Receita r1 = new Receita(u1, "Salário Mensal", "Salário", new BigDecimal("8500.00"), 5);
            r1.setId(UUID.fromString("523e4567-e89b-12d3-a456-426614174000"));

            Receita r2 = new Receita(u1, "Vale Refeição", "Benefício", new BigDecimal("900.00"), 1);
            r2.setId(UUID.fromString("523e4567-e89b-12d3-a456-426614174001"));

            Receita r3 = new Receita(u1, "Rendimentos NuBank", "Investimento", new BigDecimal("150.45"), 15);
            r3.setId(UUID.fromString("523e4567-e89b-12d3-a456-426614174002"));

            Receita r4 = new Receita(u2, "Salário", "Salário", new BigDecimal("6200.00"), 5);
            r4.setId(UUID.fromString("523e4567-e89b-12d3-a456-426614174003"));

            Receita r5 = new Receita(u2, "Auxílio Home Office", "Benefício", new BigDecimal("200.00"), 5);
            r5.setId(UUID.fromString("523e4567-e89b-12d3-a456-426614174004"));

            Receita r6 = new Receita(u2, "Venda de Bolos", "Extra", new BigDecimal("450.00"), 20);
            r6.setId(UUID.fromString("523e4567-e89b-12d3-a456-426614174005"));
            receitaRepository.saveAll(Arrays.asList(r1, r2, r3, r4, r5, r6));

            // Recurring Expenses
            GastoRecorrente gr1 = new GastoRecorrente(u1, "Cartão de Crédito", "Netflix", "Streaming",
                    new BigDecimal("55.90"));
            gr1.setId(UUID.fromString("623e4567-e89b-12d3-a456-426614174000"));

            GastoRecorrente gr2 = new GastoRecorrente(u1, "Débito Automático", "Academia SmartFit", "Saúde",
                    new BigDecimal("89.90"));
            gr2.setId(UUID.fromString("623e4567-e89b-12d3-a456-426614174001"));

            GastoRecorrente gr3 = new GastoRecorrente(u2, "PIX", "Spotify Premium", "Streaming",
                    new BigDecimal("21.90"));
            gr3.setId(UUID.fromString("623e4567-e89b-12d3-a456-426614174002"));

            GastoRecorrente gr4 = new GastoRecorrente(u2, "Cartão de Crédito", "Amazon Prime", "Streaming",
                    new BigDecimal("14.90"));
            gr4.setId(UUID.fromString("623e4567-e89b-12d3-a456-426614174003"));
            gastoRecorrenteRepository.saveAll(Arrays.asList(gr1, gr2, gr3, gr4));

            System.out.println("Database seeded successfully with Java objects!");
        }
    }
}