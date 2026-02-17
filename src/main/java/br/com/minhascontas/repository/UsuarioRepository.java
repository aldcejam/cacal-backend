package br.com.minhascontas.repository;

import br.com.minhascontas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID>, JpaSpecificationExecutor<Usuario> {
    Optional<Usuario> findByEmail(String email);
}
