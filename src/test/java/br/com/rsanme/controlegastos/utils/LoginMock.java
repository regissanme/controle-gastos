package br.com.rsanme.controlegastos.utils;

import br.com.rsanme.controlegastos.auth.domain.models.Login;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 26/12/2023
 * Hora: 12:36
 */
public class LoginMock {

    public static final String USERNAME = "user01@email.com";
    public static final String PASSWORD = "user01";

    public static Login getLogin() {
        Login login = new Login();
        login.setUsername(USERNAME);
        login.setPassword(PASSWORD);
        return login;
    }

    public static Login getBadCredentialsLogin() {
        return new Login("regi", "sub");
    }

    public static Login getLoginWithWrongPassword() {
        return new Login(USERNAME, "sub");
    }

}
