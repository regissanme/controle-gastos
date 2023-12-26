package br.com.rsanme.controlegastos.auth.domain.controllers;

import br.com.rsanme.controlegastos.auth.domain.models.Login;
import br.com.rsanme.controlegastos.utils.LoginMock;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 26/12/2023
 * Hora: 12:39
 */
@SpringBootTest
class LoginControllerIT {

    public static final String API_URL = "/api/v1/login";

    @Autowired
    WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    private String json;

    @BeforeEach
    void setUp() throws JsonProcessingException {

        mockMvc = MockMvcBuilders.
                webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        createInstances();
    }

    @Test
    void whenLoginThenReturnSuccess() throws Exception {

        mockMvc.perform(
                        post(API_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content(json)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void whenLoginWithWrongPasswordThenReturnForbidden() throws Exception {

        String login = objectMapper.writeValueAsString(LoginMock.getLoginWithWrongPassword());

        mockMvc.perform(
                        post(API_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content(login)
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void whenLoginThenReturnForbidden() throws Exception {

        String forbiddenLogin = objectMapper.writeValueAsString(LoginMock.getBadCredentialsLogin());

        mockMvc.perform(
                        post(API_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content(forbiddenLogin)
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void whenLoginThenReturnBadRequest() throws Exception {

        String badLogin = objectMapper.writeValueAsString(new Login());

        mockMvc.perform(
                        post(API_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content(badLogin)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    private void createInstances() throws JsonProcessingException {
        json = objectMapper.writeValueAsString(LoginMock.getLogin());
    }
}