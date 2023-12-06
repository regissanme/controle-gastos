package br.com.rsanme.controlegastos.controllers;

import br.com.rsanme.controlegastos.models.TipoPagamento;
import br.com.rsanme.controlegastos.services.impl.TipoPagamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 05/12/2023
 * Hora: 22:47
 */
@RestController
@RequestMapping("/api/v1/tipo-pagamento")
public class TipoPagamentoController {

    private final TipoPagamentoService service;

    public TipoPagamentoController(TipoPagamentoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TipoPagamento>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoPagamento> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<TipoPagamento> create(@RequestBody @Valid TipoPagamento tipoPagamento) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(tipoPagamento));
    }

    @PutMapping
    public ResponseEntity<TipoPagamento> update(@RequestBody @Valid TipoPagamento tipoPagamento) {
        return ResponseEntity.ok(service.update(tipoPagamento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Tipo de Pagamento excluído com sucesso!");
    }

}
