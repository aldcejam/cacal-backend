package br.com.myaccounts.service;

import br.com.myaccounts.model.RecurringExpense;
import br.com.myaccounts.model.User;
import br.com.myaccounts.repository.RecurringExpenseRepository;
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
public class RecurringExpenseService {

    @Autowired
    private RecurringExpenseRepository repository;

    @Autowired
    private UserRepository userRepository;

    public List<RecurringExpense> findAll(LocalDateTime start, LocalDateTime end) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Specification<RecurringExpense> spec = Specification
                .where(EntitySpecification.<RecurringExpense>filterByUser(email))
                .and(EntitySpecification.filterByDateRange(start, end));
        return repository.findAll(spec);
    }

    public Optional<RecurringExpense> findById(UUID id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Specification<RecurringExpense> spec = Specification
                .where(EntitySpecification.<RecurringExpense>filterByUser(email))
                .and((root, query, cb) -> cb.equal(root.get("id"), id));
        return repository.findOne(spec);
    }

    public RecurringExpense save(RecurringExpense gastoRecorrente) {
        return repository.save(gastoRecorrente);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public User findUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + userId));
    }
}
