package br.com.rsanme.controlegastos.controllers;

import br.com.rsanme.controlegastos.dtos.TipoDespesaRequest;
import br.com.rsanme.controlegastos.models.TipoDespesa;
import br.com.rsanme.controlegastos.services.impl.TipoDespesaService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/v1/despesa/tipo")
@Tag(name = "API dos Tipos de Despesas")
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
    public ResponseEntity<TipoDespesa> create(@RequestBody @Valid TipoDespesaRequest request) {
        System.out.println(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request.toModel()));
    }

    @PutMapping
    public ResponseEntity<TipoDespesa> update(@RequestBody @Valid TipoDespesaRequest request) {
        return ResponseEntity.ok(service.update(request.toModel()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Tipo de Despesa excluído com sucesso!");
    }
}
