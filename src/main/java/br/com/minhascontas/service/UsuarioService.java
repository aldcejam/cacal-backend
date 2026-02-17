package br.com.minhascontas.service;

import br.com.minhascontas.model.Usuario;
import br.com.minhascontas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public List<Usuario> findAll() {
        return repository.findAll();
    }

    public Optional<Usuario> findById(UUID id) {
        return repository.findById(id);
    }

    public Usuario save(Usuario usuario) {
        return repository.save(usuario);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
