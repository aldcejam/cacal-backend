package br.com.myaccounts.service;

import br.com.myaccounts.model.Bank;
import br.com.myaccounts.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import java.util.UUID;

@Service
public class BankService {

    @Autowired
    private BankRepository repository;

    public List<Bank> findAll() {
        return repository.findAll();
    }

    public Optional<Bank> findById(UUID id) {
        return repository.findById(id);
    }

    public Bank save(Bank banco) {
        return repository.save(banco);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
