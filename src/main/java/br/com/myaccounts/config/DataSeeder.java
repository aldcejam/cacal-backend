package br.com.myaccounts.config;

import br.com.myaccounts.model.*;
import br.com.myaccounts.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

@Component
public class DataSeeder implements CommandLineRunner {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private BankRepository bankRepository;

        @Autowired
        private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

        @Override
        @Transactional
        public void run(String... args) throws Exception {
                if (userRepository.count() == 0) {
                        // Users
                        User u1 = new User();
                        u1.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
                        u1.setName("João Silva");
                        u1.setEmail("joao@example.com");
                        u1.setPassword(passwordEncoder.encode("123456"));
                        u1.setRole("USER");

                        User u2 = new User();
                        u2.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174001"));
                        u2.setName("Maria Santos");
                        u2.setEmail("maria@example.com");
                        u2.setPassword(passwordEncoder.encode("123456"));
                        u2.setRole("USER");
                        userRepository.saveAll(Arrays.asList(u1, u2));

                        User u3 = new User();
                        u3.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174002"));
                        u3.setName("Aldc");
                        u3.setEmail("aldcejam.pro@gmail.com");
                        u3.setPassword(passwordEncoder.encode("12345678"));
                        u3.setRole("USER");
                        userRepository.save(u3);

                        // Banks
                        Bank b1 = new Bank("Nubank", new BigDecimal("5000"), "10", "30", "#8A05BE");
                        b1.setId(UUID.fromString("223e4567-e89b-12d3-a456-426614174000"));

                        Bank b2 = new Bank("C6 Bank", new BigDecimal("3000"), "15", "28", "#6348B8");
                        b2.setId(UUID.fromString("223e4567-e89b-12d3-a456-426614174001"));

                        Bank b3 = new Bank("Bank Inter", new BigDecimal("4000"), "12", "25", "#FF8800");
                        b3.setId(UUID.fromString("223e4567-e89b-12d3-a456-426614174002"));
                        bankRepository.saveAll(Arrays.asList(b1, b2, b3));

                        System.out.println("Database seeded successfully with Java objects!");
                }
        }
}