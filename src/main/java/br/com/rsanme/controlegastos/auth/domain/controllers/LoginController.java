package br.com.rsanme.controlegastos.auth.domain.controllers;

import br.com.rsanme.controlegastos.auth.domain.dtos.UserResponse;
import br.com.rsanme.controlegastos.auth.domain.models.Login;
import br.com.rsanme.controlegastos.auth.domain.models.UserApp;
import br.com.rsanme.controlegastos.auth.domain.services.impl.UserAppService;
import br.com.rsanme.controlegastos.auth.jwt.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 18/12/2023
 * Hora: 23:59
 */
@RestController
@RequestMapping("api/v1/login")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserAppService userAppService;

    public LoginController(AuthenticationManager authenticationManager, TokenService tokenService, UserAppService userAppService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userAppService = userAppService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> login(@RequestBody @Valid Login login) {

        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        var principal = (UserApp) authenticate.getPrincipal();
        userAppService.setLastAccessAt(principal);
        String token = tokenService.getToken(principal);
        Date expiration = tokenService.getExpiration(token);
        UserResponse response = UserResponse.toResponse(principal, token, expiration);

        return ResponseEntity.ok(response);
    }
}
