package br.com.minhascontas.service;

import br.com.minhascontas.model.Cartao;
import br.com.minhascontas.model.Transacao;
import br.com.minhascontas.repository.CartaoRepository;
import br.com.minhascontas.repository.TransacaoRepository;
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
public class TransacaoService {

    @Autowired
    private TransacaoRepository repository;

    @Autowired
    private CartaoRepository cartaoRepository;

    public List<Transacao> findAll(LocalDateTime start, LocalDateTime end) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Specification<Transacao> spec = Specification.where(EntitySpecification.<Transacao>filterByUser(email))
                .and(EntitySpecification.filterByDateRange(start, end));
        return repository.findAll(spec);
    }

    public Optional<Transacao> findById(UUID id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Specification<Transacao> spec = Specification.where(EntitySpecification.<Transacao>filterByUser(email))
                .and((root, query, cb) -> cb.equal(root.get("id"), id));
        return repository.findOne(spec);
    }

    public Transacao save(Transacao transacao) {
        return repository.save(transacao);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public Cartao findCartaoById(UUID cardId) {
        return cartaoRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Cartão não encontrado: " + cardId));
    }
}
