package br.com.myaccounts.service;

import br.com.myaccounts.model.Card;
import br.com.myaccounts.model.Transaction;
import br.com.myaccounts.repository.CardRepository;
import br.com.myaccounts.repository.TransactionRepository;
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
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private CardRepository cardRepository;

    public List<Transaction> findAll(LocalDateTime start, LocalDateTime end) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Specification<Transaction> spec = Specification.where(EntitySpecification.<Transaction>filterByUser(email))
                .and(EntitySpecification.filterByDateRange(start, end));
        return repository.findAll(spec);
    }

    public Optional<Transaction> findById(UUID id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Specification<Transaction> spec = Specification.where(EntitySpecification.<Transaction>filterByUser(email))
                .and((root, query, cb) -> cb.equal(root.get("id"), id));
        return repository.findOne(spec);
    }

    public Transaction save(Transaction transacao) {
        return repository.save(transacao);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public Card findCardById(UUID cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Cartão não encontrado: " + cardId));
    }
}
