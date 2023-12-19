package br.com.rsanme.controlegastos.controllers;

import br.com.rsanme.controlegastos.dtos.UserRequest;
import br.com.rsanme.controlegastos.dtos.UserResponse;
import br.com.rsanme.controlegastos.models.UserApp;
import br.com.rsanme.controlegastos.services.impl.UserAppService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 18/12/2023
 * Hora: 20:06
 */
@RestController
@RequestMapping("api/v1/users")
public class UserAppController {

    private final UserAppService service;

    public UserAppController(UserAppService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        return ResponseEntity.ok(UserResponse.toListResponse(service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(UserResponse.toResponse(service.findById(id), null));
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest userRequest) {
        UserApp userApp = service.create(userRequest.toModel());
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.toResponse(userApp, null));
    }

    @PutMapping()
    public ResponseEntity<UserResponse> update(@RequestBody @Valid UserRequest userRequest) {
        UserApp userApp = service.update(userRequest.toModel());
        return ResponseEntity.ok(UserResponse.toResponse(userApp, null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Usuário removido com sucesso com id: " + id);
    }

    @PutMapping("/enable/{id}")
    public ResponseEntity<String> enableUser(@PathVariable Long id) {
        service.enableUser(id);
        return ResponseEntity.ok("Ativação com sucesso do Usuário com id: " + id);
    }

    @PutMapping("/disable/{id}")
    public ResponseEntity<String> disableUser(@PathVariable Long id) {
        service.disableUser(id);
        return ResponseEntity.ok("Desativação com sucesso do Usuário com id: " + id);
    }


}
