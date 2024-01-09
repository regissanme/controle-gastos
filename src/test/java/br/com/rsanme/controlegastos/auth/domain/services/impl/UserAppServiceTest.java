package br.com.rsanme.controlegastos.auth.domain.services.impl;

import br.com.rsanme.controlegastos.auth.domain.models.UserApp;
import br.com.rsanme.controlegastos.auth.domain.repositories.UserAppRepository;
import br.com.rsanme.controlegastos.exceptions.CustomEntityAlreadyExistsException;
import br.com.rsanme.controlegastos.exceptions.CustomEntityNotFoundException;
import br.com.rsanme.controlegastos.utils.UserAppMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 20/12/2023
 * Hora: 17:13
 */
@ExtendWith(MockitoExtension.class)
class UserAppServiceTest {

    @InjectMocks
    private UserAppService service;

    @Mock
    private UserAppRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserApp userApp;

    @BeforeEach
    void setUp() {
        createInstances();
    }

    @Test
    void whenFindAllThenReturnList() {

        when(repository.findAll()).thenReturn(List.of(userApp));

        List<UserApp> listResponse = service.findAll();

        assertNotNull(listResponse);
        assertEquals(1, listResponse.size());
        assertEquals(UserApp.class, listResponse.get(0).getClass());

        verify(repository).findAll();
    }

    @Test
    void whenFindByIdThenReturnInstance() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(userApp));

        UserApp response = service.findById(UserAppMock.ID);

        assertNotNull(response);
        assertEquals(UserAppMock.ID, response.getId());
        assertEquals(UserApp.class, response.getClass());

        verify(repository).findById(anyLong());
    }

    @Test
    void whenFindByIdThenThrowsNotFound() {

        assertThatThrownBy(() -> service.findById(UserAppMock.ID))
                .hasMessage(UserAppMock.ERROR_MESSAGE_NOT_FOUND)
                .isInstanceOf(CustomEntityNotFoundException.class);

        verify(repository).findById(anyLong());
    }

    @Test
    void whenCreateThenReturnInstance() {

        UserApp toSave = UserAppMock.userAppToSave();

        when(repository.save(any(UserApp.class))).thenReturn(userApp);

        UserApp response = service.create(toSave);

        assertNotNull(response);
        assertEquals(UserApp.class, response.getClass());
        assertEquals(UserAppMock.ID, response.getId());
        assertEquals(UserAppMock.USERNAME, response.getUsername());
        assertEquals(UserAppMock.PASSWORD, response.getPassword());
        assertEquals(UserAppMock.NAME, response.getName());
        assertEquals(UserAppMock.BIRTHDATE, response.getBirthDate());
        assertEquals(UserAppMock.ROLE, response.getRole());
        assertEquals(UserAppMock.ACTIVE, response.getActive());
        assertEquals(1, response.getAuthorities().size());
        assertEquals(UserAppMock.ACTIVE, response.isAccountNonExpired());
        assertEquals(UserAppMock.ACTIVE, response.isAccountNonLocked());
        assertEquals(UserAppMock.ACTIVE, response.isCredentialsNonExpired());
        assertEquals(UserAppMock.ACTIVE, response.isEnabled());

        assertTrue(userApp.equals(response));
        assertFalse(userApp.equals(null));
        assertFalse(userApp.equals(toSave));
        assertEquals(userApp.hashCode(), response.hashCode());
        assertTrue(userApp.toString().contains("UserApp"));

        verify(repository).save(any(UserApp.class));
    }

    @Test
    void whenCreateThenThrowsAlreadyExists() {

        UserApp toSave = UserAppMock.userAppToSave();

        when(repository.findByUsername(anyString())).thenReturn(userApp);

        assertThatThrownBy(() -> service.create(toSave))
                .hasMessage(UserAppMock.ERROR_MESSAGE_ALREADY_EXISTS)
                .isInstanceOf(CustomEntityAlreadyExistsException.class);

        verify(repository).findByUsername(anyString());
        verify(repository, never()).save(any(UserApp.class));
    }

    @Test
    void whenUpdateThenReturnInstance() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(userApp));
        when(repository.save(any(UserApp.class))).thenReturn(userApp);

        UserApp response = service.update(UserAppMock.userAppToUpdate());

        assertNotNull(response);

        verify(repository).findById(anyLong());
        verify(repository).save(any(UserApp.class));
    }

    @Test
    void whenUpdateThenThrowsNotFound() {

        assertThatThrownBy(() -> service.update(userApp))
                .hasMessage(UserAppMock.ERROR_MESSAGE_NOT_FOUND)
                .isInstanceOf(CustomEntityNotFoundException.class);

        verify(repository).findById(anyLong());
        verify(repository, never()).save(any(UserApp.class));
    }

    @Test
    void whenUpdateThenThrowsAlreadyExists() {

        userApp.setId(2L);
        when(repository.findById(anyLong())).thenReturn(Optional.of(UserAppMock.getUserApp()));
        when(repository.findByUsername(anyString())).thenReturn(userApp);

        assertThatThrownBy(() -> service.update(UserAppMock.getUserApp()))
                .hasMessage(UserAppMock.ERROR_MESSAGE_ALREADY_EXISTS)
                .isInstanceOf(CustomEntityAlreadyExistsException.class);

        verify(repository).findById(anyLong());
        verify(repository).findByUsername(anyString());
        verify(repository, never()).save(any(UserApp.class));
    }

    @Test
    void whenDeleteThenSuccess() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(userApp));

        service.delete(UserAppMock.ID);

        verify(repository).findById(anyLong());
        verify(repository).deleteById(anyLong());
    }

    @Test
    void whenDeleteThenThrowsNotFound() {

        assertThatThrownBy(() -> service.delete(UserAppMock.ID))
                .hasMessage(UserAppMock.ERROR_MESSAGE_NOT_FOUND)
                .isInstanceOf(CustomEntityNotFoundException.class);

        verify(repository).findById(anyLong());
        verify(repository, never()).deleteById(anyLong());
    }

    @Test
    void whenEnableUserThenSuccess() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(userApp));

        service.enableUser(UserAppMock.ID);

        verify(repository).findById(anyLong());
        verify(repository).save(any(UserApp.class));
    }

    @Test
    void whenEnableUserThenThrowsNotFound() {

        assertThatThrownBy(() -> service.enableUser(UserAppMock.ID))
                .hasMessage(UserAppMock.ERROR_MESSAGE_NOT_FOUND)
                .isInstanceOf(CustomEntityNotFoundException.class);

        verify(repository).findById(anyLong());
        verify(repository, never()).save(any(UserApp.class));
    }

    @Test
    void whenDisableUserThenSuccess() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(userApp));

        service.disableUser(UserAppMock.ID);

        verify(repository).findById(anyLong());
        verify(repository).save(any(UserApp.class));
    }

    @Test
    void whenDisableUserThenThrowsNotFound() {

        assertThatThrownBy(() -> service.disableUser(UserAppMock.ID))
                .hasMessage(UserAppMock.ERROR_MESSAGE_NOT_FOUND)
                .isInstanceOf(CustomEntityNotFoundException.class);

        verify(repository).findById(anyLong());
        verify(repository, never()).save(any(UserApp.class));
    }

    @Test
    void whenUserSetLastAccessAtThenSuccess() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(userApp));

        service.setLastAccessAt(userApp);

        verify(repository).findById(anyLong());
        verify(repository).save(any(UserApp.class));
    }

    @Test
    void whenUserSetLastAccessAtThenThrowsNotFound() {

        assertThatThrownBy(() -> service.setLastAccessAt(userApp))
                .hasMessage(UserAppMock.ERROR_MESSAGE_NOT_FOUND)
                .isInstanceOf(CustomEntityNotFoundException.class);

        verify(repository).findById(anyLong());
        verify(repository, never()).save(any(UserApp.class));
    }


    private void createInstances() {
        userApp = UserAppMock.getUserApp();
    }
}