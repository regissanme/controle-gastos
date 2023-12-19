package br.com.rsanme.controlegastos.auth.domain.services;

import br.com.rsanme.controlegastos.auth.domain.models.UserApp;

import java.util.List;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 18/12/2023
 * Hora: 23:38
 */
public interface IUserCrudService {

    List<UserApp> findAll();
    UserApp findById(Long id);
    UserApp create(UserApp userApp);
    UserApp update(UserApp userApp);
    void delete(Long id);
    void disableUser(Long id);
    void enableUser(Long id);
    void setLastAccessAt(UserApp userApp);
}
