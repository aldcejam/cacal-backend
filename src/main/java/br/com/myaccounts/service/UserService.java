package br.com.myaccounts.service;

import br.com.myaccounts.model.User;
import br.com.myaccounts.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public Optional<User> findById(UUID id) {
        return repository.findById(id);
    }

    public User save(User usuario) {
        return repository.save(usuario);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
