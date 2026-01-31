package br.com.minhascontas.service;

import br.com.minhascontas.model.Transacao;
import br.com.minhascontas.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository repository;

    public List<Transacao> findAll() {
        return repository.findAll();
    }

    public Optional<Transacao> findById(String id) {
        return repository.findById(id);
    }

    public Transacao save(Transacao transacao) {
        return repository.save(transacao);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
