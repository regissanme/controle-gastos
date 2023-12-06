package br.com.rsanme.controlegastos.controllers;

import br.com.rsanme.controlegastos.models.TipoDespesa;
import br.com.rsanme.controlegastos.services.impl.TipoDespesaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 06/12/2023
 * Hora: 15:12
 */
@RestController
@RequestMapping("/api/v1/tipo-despesa")
public class TipoDespesaController {

    private final TipoDespesaService service;

    public TipoDespesaController(TipoDespesaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TipoDespesa>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoDespesa> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<TipoDespesa> create(@RequestBody @Valid TipoDespesa tipoDespesa) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(tipoDespesa));
    }

    @PutMapping
    public ResponseEntity<TipoDespesa> update(@RequestBody @Valid TipoDespesa tipoDespesa) {
        return ResponseEntity.ok(service.update(tipoDespesa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Tipo de Despesa excluído com sucesso!");
    }
}
