package br.com.minhascontas.controller;

import br.com.minhascontas.model.GastoRecorrente;
import br.com.minhascontas.service.GastoRecorrenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/gastosRecorrentes") // Updated to match db.json key if needed or camelCase

public class GastoRecorrenteController {

    @Autowired
    private GastoRecorrenteService service;

    @GetMapping
    public ResponseEntity<List<GastoRecorrente>> findAll(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        return ResponseEntity.ok(service.findAll(start, end));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GastoRecorrente> findById(@PathVariable UUID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GastoRecorrente> save(@RequestBody GastoRecorrente gastoRecorrente) {
        return ResponseEntity.ok(service.save(gastoRecorrente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
