package br.com.minhascontas.service;

import br.com.minhascontas.model.Receita;
import br.com.minhascontas.repository.ReceitaRepository;
import br.com.minhascontas.specification.EntitySpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReceitaService {

    @Autowired
    private ReceitaRepository repository;

    public List<Receita> findAll(LocalDateTime start, LocalDateTime end) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Specification<Receita> spec = Specification.where(EntitySpecification.<Receita>filterByUser(email))
                .and(EntitySpecification.filterByDateRange(start, end));
        return repository.findAll(spec);
    }

    public Optional<Receita> findById(UUID id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Specification<Receita> spec = Specification.where(EntitySpecification.<Receita>filterByUser(email))
                .and((root, query, cb) -> cb.equal(root.get("id"), id));
        return repository.findOne(spec);
    }

    public Receita save(Receita receita) {
        return repository.save(receita);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
