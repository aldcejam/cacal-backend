package br.com.minhascontas.service;

import br.com.minhascontas.model.Cartao;
import br.com.minhascontas.repository.CartaoRepository;
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
public class CartaoService {

    @Autowired
    private CartaoRepository repository;

    public List<Cartao> findAll(LocalDateTime start, LocalDateTime end) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Specification<Cartao> spec = Specification.where(EntitySpecification.<Cartao>filterByUser(email))
                .and(EntitySpecification.filterByDateRange(start, end));
        return repository.findAll(spec);
    }

    public Optional<Cartao> findById(UUID id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Specification<Cartao> spec = Specification.where(EntitySpecification.<Cartao>filterByUser(email))
                .and((root, query, cb) -> cb.equal(root.get("id"), id));
        return repository.findOne(spec);
    }

    public Cartao save(Cartao cartao) {
        return repository.save(cartao);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
