package br.com.minhascontas.service;

import br.com.minhascontas.model.Receita;
import br.com.minhascontas.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReceitaService {

    @Autowired
    private ReceitaRepository repository;

    public List<Receita> findAll() {
        return repository.findAll();
    }

    public Optional<Receita> findById(String id) {
        return repository.findById(id);
    }

    public Receita save(Receita receita) {
        return repository.save(receita);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
