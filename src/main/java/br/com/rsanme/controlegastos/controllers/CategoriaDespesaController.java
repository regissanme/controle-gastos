package br.com.rsanme.controlegastos.controllers;

import br.com.rsanme.controlegastos.models.CategoriaDespesa;
import br.com.rsanme.controlegastos.models.TipoDespesa;
import br.com.rsanme.controlegastos.services.impl.CategoriaDespesaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 06/12/2023
 * Hora: 23:11
 */
@RestController
@RequestMapping("/api/v1/despesa/categoria")
public class CategoriaDespesaController {

    private final CategoriaDespesaService service;

    public CategoriaDespesaController(CategoriaDespesaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDespesa>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDespesa> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<CategoriaDespesa> create(@RequestBody @Valid CategoriaDespesa categoriaDespesa) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(categoriaDespesa));
    }

    @PutMapping
    public ResponseEntity<CategoriaDespesa> update(@RequestBody @Valid CategoriaDespesa categoriaDespesa) {
        return ResponseEntity.ok(service.update(categoriaDespesa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Categoria de Despesa excluída com sucesso!");
    }

}
