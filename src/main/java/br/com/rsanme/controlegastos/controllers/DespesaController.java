package br.com.rsanme.controlegastos.controllers;

import br.com.rsanme.controlegastos.dtos.DespesaRequest;
import br.com.rsanme.controlegastos.dtos.DespesaResponse;
import br.com.rsanme.controlegastos.models.Despesa;
import br.com.rsanme.controlegastos.services.impl.DespesaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/all/{userId}")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<List<DespesaResponse>> findAll(@PathVariable Long userId) {
        return ResponseEntity.ok(DespesaResponse.toListResponse(service.findAllByUser(userId)));
    }

    @GetMapping("/all/{userId}/{year}")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<List<DespesaResponse>> findAllByYear(@PathVariable Long userId, @PathVariable int year) {
        return ResponseEntity.ok(DespesaResponse.toListResponse(service.findAllByUserAndYear(userId, year)));
    }

    @GetMapping("/years/{userId}")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<List<Integer>> findAllYears(@PathVariable Long userId) {
        return ResponseEntity.ok(service.findDistinctYearFromDataByUserId(userId));
    }

    @GetMapping("/months/{userId}/{year}")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<List<Integer>> findAllMonthsByYear(@PathVariable Long userId, @PathVariable Integer year) {
        return ResponseEntity.ok(service.findDistinctMonthFromDataByUserIdAndYear(userId, year));
    }

    @GetMapping("/all/{userId}/{year}/{month}")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<List<DespesaResponse>> findAllByYearAndMonth(
            @PathVariable Long userId,
            @PathVariable int year,
            @PathVariable int month
    ) {
        return ResponseEntity.ok(
                DespesaResponse.toListResponse(service.findAllByUserAndYearAndMonth(userId, year, month))
        );
    }

    @GetMapping("/{id}")
    @PostAuthorize("returnObject.body.userId == authentication.principal.id")
    public ResponseEntity<DespesaResponse> findById(@PathVariable Long id) {
        Despesa despesa = service.findById(id);
        return ResponseEntity.ok(DespesaResponse.toResponse(despesa));
    }

    @PostMapping
    @PreAuthorize("#toCreate.userId == authentication.principal.id")
    public ResponseEntity<DespesaResponse> create(@RequestBody @Valid DespesaRequest toCreate) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DespesaResponse.toResponse(service.create(toCreate.toModel())));
    }

    @PutMapping
    @PreAuthorize("#toUpdate.userId == authentication.principal.id")
    public ResponseEntity<DespesaResponse> update(@RequestBody @Valid DespesaRequest toUpdate) {
        return ResponseEntity.ok(DespesaResponse.toResponse(service.update(toUpdate.toModel())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Despesa excluída com sucesso!");
    }

}
