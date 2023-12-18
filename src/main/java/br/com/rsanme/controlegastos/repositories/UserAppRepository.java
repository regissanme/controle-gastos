package br.com.rsanme.controlegastos.repositories;

import br.com.rsanme.controlegastos.models.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 16/12/2023
 * Hora: 17:35
 */
public interface UserAppRepository extends JpaRepository<UserApp, Long> {

    UserApp findByUsername(String username);
}
