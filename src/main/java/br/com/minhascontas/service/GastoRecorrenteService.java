package br.com.minhascontas.service;

import br.com.minhascontas.model.GastoRecorrente;
import br.com.minhascontas.repository.GastoRecorrenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GastoRecorrenteService {

    @Autowired
    private GastoRecorrenteRepository repository;

    public List<GastoRecorrente> findAll() {
        return repository.findAll();
    }

    public Optional<GastoRecorrente> findById(String id) {
        return repository.findById(id);
    }

    public GastoRecorrente save(GastoRecorrente gastoRecorrente) {
        return repository.save(gastoRecorrente);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
