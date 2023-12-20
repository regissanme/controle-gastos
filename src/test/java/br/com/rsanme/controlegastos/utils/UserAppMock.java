package br.com.rsanme.controlegastos.utils;

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

    public static final Long ID = 1L;
    public static final String USERNAME = "user01";
    public static final String PASSWORD = "user01password";
    public static final String NAME = "User 01";
    public static final String CPF = "11122233344";
    public static final LocalDate BIRTHDATE = LocalDate.of(1978, 11, 14);
    public static final String ROLE = "ROLE_USER";
    public static final Boolean ACTIVE = true;
    public static final LocalDateTime CREATED_AT = LocalDateTime.now().minusDays(1L);
    public static final LocalDateTime UPDATE_AT = LocalDateTime.now().minusHours(1L);
    public static final LocalDateTime LAST_ACCESS_AT = LocalDateTime.now().minusMinutes(40L);
    public static final LocalDateTime CURRENT_ACCESS_AT = LocalDateTime.now();
    public static final String ERROR_MESSAGE_ALREADY_EXISTS = "Já existe um usuário com username: " + USERNAME;
    public static final String ERROR_MESSAGE_NOT_FOUND = "Nenhum usuário encontrado com Id: " + ID;
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

}
