package br.com.rsanme.controlegastos.controllers;

import br.com.rsanme.controlegastos.dtos.DespesaRequest;
import br.com.rsanme.controlegastos.models.Despesa;
import br.com.rsanme.controlegastos.services.impl.DespesaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 11/12/2023
 * Hora: 23:22
 */
@RestController
@RequestMapping("/api/v1/despesa")
@Tag(name = "API das Despesas")
public class DespesaController {

    private final DespesaService service;

    public DespesaController(DespesaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Despesa>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Despesa> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Despesa> create(@RequestBody @Valid DespesaRequest toCreate) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(toCreate.toModel()));
    }

    @PutMapping
    public ResponseEntity<Despesa> update(@RequestBody @Valid DespesaRequest toUpdate) {
        return ResponseEntity.ok(service.update(toUpdate.toModel()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Despesa excluída com sucesso!");
    }

}