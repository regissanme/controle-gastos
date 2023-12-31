package br.com.rsanme.controlegastos.auth.domain.repositories;

import br.com.rsanme.controlegastos.auth.domain.models.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 16/12/2023
 * Hora: 17:35
 */
public interface UserAppRepository extends JpaRepository<UserApp, Long> {

    UserDetails findByUsername(String username);
}
