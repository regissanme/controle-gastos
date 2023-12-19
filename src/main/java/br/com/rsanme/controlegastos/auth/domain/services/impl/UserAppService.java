package br.com.rsanme.controlegastos.auth.domain.services.impl;

import br.com.rsanme.controlegastos.auth.domain.services.IUserCrudService;
import br.com.rsanme.controlegastos.exceptions.CustomEntityAlreadyExistsException;
import br.com.rsanme.controlegastos.exceptions.CustomEntityNotFoundException;
import br.com.rsanme.controlegastos.auth.domain.models.UserApp;
import br.com.rsanme.controlegastos.auth.domain.repositories.UserAppRepository;
import br.com.rsanme.controlegastos.services.ICrudService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 16/12/2023
 * Hora: 17:36
 */
@Service
public class UserAppService implements IUserCrudService {

    private final UserAppRepository repository;

    public UserAppService(UserAppRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UserApp> findAll() {
        return repository.findAll();
    }

    @Override
    public UserApp findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new CustomEntityNotFoundException(
                        String.format("Usuário com Id %s não encontrado!", id))
        );
    }

    @Override
    public UserApp create(UserApp userApp) {

        usernameExists(userApp);

        userApp.setRole("ROLE_USER");
        userApp.setActive(true);
        userApp.setCreatedAt(LocalDateTime.now());

        return repository.save(userApp);
    }

    @Override
    public UserApp update(UserApp userApp) {
        UserApp toUpdate = findById(userApp.getId());
        usernameExists(userApp);

        toUpdate.setName(userApp.getName());
        toUpdate.setPassword(userApp.getPassword());
        toUpdate.setUsername(userApp.getUsername());
        toUpdate.setBirthDate(userApp.getBirthDate());
        toUpdate.setUpdatedAt(LocalDateTime.now());

        return repository.save(toUpdate);
    }

    @Override
    public void delete(Long id) {
        findById(id);
        repository.deleteById(id);
    }

    public void disableUser(Long id) {
        UserApp toDisable = findById(id);
        toDisable.setActive(false);
        update(toDisable);
    }

    public void enableUser(Long id) {
        UserApp toDisable = findById(id);
        toDisable.setActive(true);
        update(toDisable);
    }

    private void usernameExists(UserApp userApp) {
        UserApp byUsername = repository.findByUsername(userApp.getUsername());
        if (byUsername != null && !byUsername.getId().equals(userApp.getId())) {
            throw new CustomEntityAlreadyExistsException(
                    String.format("Já existe um usuário com username %s", userApp.getUsername())
            );
        }
    }
}
