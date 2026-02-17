package br.com.minhascontas.repository;

import br.com.minhascontas.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, UUID>, JpaSpecificationExecutor<Transacao> {
}
