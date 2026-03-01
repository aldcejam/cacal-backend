package br.com.minhascontas.service;

import br.com.minhascontas.model.GastoRecorrente;
import br.com.minhascontas.model.Usuario;
import br.com.minhascontas.repository.GastoRecorrenteRepository;
import br.com.minhascontas.repository.UsuarioRepository;
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
public class GastoRecorrenteService {

    @Autowired
    private GastoRecorrenteRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<GastoRecorrente> findAll(LocalDateTime start, LocalDateTime end) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Specification<GastoRecorrente> spec = Specification
                .where(EntitySpecification.<GastoRecorrente>filterByUser(email))
                .and(EntitySpecification.filterByDateRange(start, end));
        return repository.findAll(spec);
    }

    public Optional<GastoRecorrente> findById(UUID id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Specification<GastoRecorrente> spec = Specification
                .where(EntitySpecification.<GastoRecorrente>filterByUser(email))
                .and((root, query, cb) -> cb.equal(root.get("id"), id));
        return repository.findOne(spec);
    }

    public GastoRecorrente save(GastoRecorrente gastoRecorrente) {
        return repository.save(gastoRecorrente);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public Usuario findUsuarioById(UUID userId) {
        return usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + userId));
    }
}
