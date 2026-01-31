package br.com.minhascontas.controller;

import br.com.minhascontas.model.GastoRecorrente;
import br.com.minhascontas.service.GastoRecorrenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gastosRecorrentes") // Updated to match db.json key if needed or camelCase
@CrossOrigin(origins = "*")
public class GastoRecorrenteController {

    @Autowired
    private GastoRecorrenteService service;

    @GetMapping
    public List<GastoRecorrente> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GastoRecorrente> findById(@PathVariable String id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public GastoRecorrente save(@RequestBody GastoRecorrente gastoRecorrente) {
        return service.save(gastoRecorrente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
