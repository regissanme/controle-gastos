package br.com.rsanme.controlegastos.utils;

import br.com.rsanme.controlegastos.auth.domain.dtos.UserRequest;
import br.com.rsanme.controlegastos.auth.domain.models.UserApp;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 20/12/2023
 * Hora: 17:13
 */
public class UserAppMock {

    public static final Long ID = 3L;
    public static final String USERNAME = "user02";
    public static final String USERNAME_ADMIN = "regis";
    public static final String USERNAME_ALREADY_EXISTS = "user01";
    public static final String PASSWORD = "user02password";
    public static final String PASSWORD_ADMIN = "subsanme";
    public static final String NAME = "User 02";
    public static final String CPF = "86039598115";
    public static final LocalDate BIRTHDATE = LocalDate.of(1978, 11, 14);
    public static final String ROLE = "ROLE_USER";
    public static final Boolean ACTIVE = true;
    public static final LocalDateTime CREATED_AT = LocalDateTime.now().minusDays(1L);
    public static final LocalDateTime UPDATE_AT = LocalDateTime.now().minusHours(1L);
    public static final LocalDateTime LAST_ACCESS_AT = LocalDateTime.now().minusMinutes(40L);
    public static final LocalDateTime CURRENT_ACCESS_AT = LocalDateTime.now();
    public static final String ERROR_MESSAGE_ALREADY_EXISTS = "Já existe um usuário com username: " + USERNAME;
    public static final String ERROR_MESSAGE_NOT_FOUND = "Nenhum usuário encontrado com Id: " + ID;
    public static final String ENABLE_SUCCESS_MESSAGE = "Ativação com sucesso do Usuário com id: " + ID;
    public static final String DISABLE_SUCCESS_MESSAGE = "Desativação com sucesso do Usuário com id: " + ID;
    public static final String ERRO_INVALID_DATA = "Dados inválidos!";

    public static UserApp getUserApp() {

        UserApp userApp = new UserApp();
        userApp.setId(ID);
        userApp.setUsername(USERNAME);
        userApp.setPassword(PASSWORD);
        userApp.setName(NAME);
        userApp.setCpf(CPF);
        userApp.setBirthDate(BIRTHDATE);
        userApp.setRole(ROLE);
        userApp.setActive(ACTIVE);

        return userApp;
    }

    public static UserApp userAppToSave() {
        UserApp toSave = getUserApp();
        toSave.setId(0L);
        return toSave;
    }

    public static UserApp userAppToUpdate() {
        return new UserApp(
                ID,
                USERNAME,
                "new password",
                NAME,
                CPF,
                BIRTHDATE,
                ACTIVE,
                ROLE,
                CREATED_AT,
                UPDATE_AT,
                LAST_ACCESS_AT,
                CURRENT_ACCESS_AT);
    }

    public static UserApp getAdminUser() {
        UserApp admin = getUserApp();
        admin.setUsername(USERNAME_ADMIN);
        admin.setPassword(PASSWORD_ADMIN);
        admin.setRole("ROLE_ADMIN");
        return admin;
    }

    public static UserRequest getRequest() {
        UserRequest request = new UserRequest();
        request.setUsername(USERNAME);
        request.setPassword(PASSWORD);
        request.setName(NAME);
        request.setCpf(CPF);
        request.setBirthDate(BIRTHDATE);

        return request;
    }

    public static UserRequest getRequestToUpdate() {
        UserRequest request = getRequest();
        request.setId(ID);
        return request;
    }

}
