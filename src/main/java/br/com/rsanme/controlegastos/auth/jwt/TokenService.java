package br.com.rsanme.controlegastos.auth.jwt;

import br.com.rsanme.controlegastos.auth.domain.models.UserApp;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 18/12/2023
 * Hora: 22:29
 */
@Component
public class TokenService {

    @Value("secret")
    private String secret;

    @Value("issuer")
    private String issuer;

    public String getToken(UserApp userApp) {
        return JWT
                .create()
                .withIssuer(issuer)
                .withSubject(userApp.getUsername())
                .withClaim("id", userApp.getId())
                .withExpiresAt(getExpiration())
                .sign(Algorithm.HMAC256(secret));
    }

    public String getSubject(String token) {
        return JWT
                .require(Algorithm.HMAC256(secret))
                .withIssuer(issuer)
                .build()
                .verify(token)
                .getSubject();
    }

    private Instant getExpiration() {
        return LocalDateTime
                .now()
                .plusHours(2L)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
