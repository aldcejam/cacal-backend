package br.com.minhascontas.repository;

import br.com.minhascontas.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, UUID>, JpaSpecificationExecutor<Receita> {
}
