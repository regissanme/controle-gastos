package br.com.rsanme.controlegastos.auth.jwt;

import br.com.rsanme.controlegastos.auth.domain.services.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 18/12/2023
 * Hora: 23:11
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final AuthService authService;

    public TokenFilter(TokenService tokenService, AuthService authService) {
        this.tokenService = tokenService;
        this.authService = authService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null) {
            String token = authorizationHeader.replace("Bearer ", "");
            String subject = tokenService.getSubject(token);
            UserDetails user = authService.loadUserByUsername(subject);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
