package br.com.minhascontas.service;

import br.com.minhascontas.model.Cartao;
import br.com.minhascontas.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository repository;

    public List<Cartao> findAll() {
        return repository.findAll();
    }

    public Optional<Cartao> findById(String id) {
        return repository.findById(id);
    }

    public Cartao save(Cartao cartao) {
        return repository.save(cartao);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
