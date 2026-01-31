package br.com.minhascontas.repository;

import br.com.minhascontas.model.GastoRecorrente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GastoRecorrenteRepository extends JpaRepository<GastoRecorrente, String> {
}
