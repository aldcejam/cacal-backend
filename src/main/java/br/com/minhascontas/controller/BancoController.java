package br.com.minhascontas.controller;

import br.com.minhascontas.model.Banco;
import br.com.minhascontas.service.BancoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bancos")
@CrossOrigin(origins = "*")
public class BancoController {

    @Autowired
    private BancoService service;

    @GetMapping
    public List<Banco> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Banco> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Banco save(@RequestBody Banco banco) {
        return service.save(banco);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
