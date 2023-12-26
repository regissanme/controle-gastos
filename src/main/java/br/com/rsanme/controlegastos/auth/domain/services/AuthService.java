package br.com.rsanme.controlegastos.auth.domain.services;

import br.com.rsanme.controlegastos.auth.domain.repositories.UserAppRepository;
import br.com.rsanme.controlegastos.exceptions.CustomUsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 18/12/2023
 * Hora: 23:47
 */
@Service
public class AuthService implements UserDetailsService {

    private final UserAppRepository repository;

    public AuthService(UserAppRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws CustomUsernameNotFoundException {
        UserDetails user = repository.findByUsername(username);
        if (user == null) {
            throw new CustomUsernameNotFoundException("Não encontrado nenhum usuário com o username: " + username);
        }
        return user;
    }
}
