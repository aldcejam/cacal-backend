package br.com.minhascontas.service;

import br.com.minhascontas.model.Banco;
import br.com.minhascontas.repository.BancoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import java.util.UUID;

@Service
public class BancoService {

    @Autowired
    private BancoRepository repository;

    public List<Banco> findAll() {
        return repository.findAll();
    }

    public Optional<Banco> findById(UUID id) {
        return repository.findById(id);
    }

    public Banco save(Banco banco) {
        return repository.save(banco);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
