package br.com.minhascontas.repository;

import br.com.minhascontas.model.GastoRecorrente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GastoRecorrenteRepository extends JpaRepository<GastoRecorrente, UUID>, JpaSpecificationExecutor<GastoRecorrente> {
}
