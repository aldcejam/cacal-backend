package br.com.myaccounts.service;

import br.com.myaccounts.model.Bank;
import br.com.myaccounts.model.Card;
import br.com.myaccounts.model.User;
import br.com.myaccounts.repository.BankRepository;
import br.com.myaccounts.repository.CardRepository;
import br.com.myaccounts.repository.UserRepository;
import br.com.myaccounts.specification.EntitySpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CardService {

    @Autowired
    private CardRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankRepository bankRepository;

    public List<Card> findAll(LocalDateTime start, LocalDateTime end) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Specification<Card> spec = Specification.where(EntitySpecification.<Card>filterByUser(email))
                .and(EntitySpecification.filterByDateRange(start, end));
        return repository.findAll(spec);
    }

    public Optional<Card> findById(UUID id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Specification<Card> spec = Specification.where(EntitySpecification.<Card>filterByUser(email))
                .and((root, query, cb) -> cb.equal(root.get("id"), id));
        return repository.findOne(spec);
    }

    public Card save(Card cartao) {
        return repository.save(cartao);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public User findUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + userId));
    }

    public Bank findBankById(UUID bankId) {
        return bankRepository.findById(bankId)
                .orElseThrow(() -> new RuntimeException("Bank não encontrado: " + bankId));
    }
}
