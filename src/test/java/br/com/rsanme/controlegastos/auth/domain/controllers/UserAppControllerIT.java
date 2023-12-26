package br.com.rsanme.controlegastos.auth.domain.controllers;

import br.com.rsanme.controlegastos.auth.domain.dtos.UserRequest;
import br.com.rsanme.controlegastos.auth.jwt.TokenService;
import br.com.rsanme.controlegastos.utils.UserAppMock;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 26/12/2023
 * Hora: 10:27
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserAppControllerIT {

    public static final String API_URL = "/api/v1/users";

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    private String token;
    private String json;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        createInstances();
    }

    @Test
    @Order(1)
    void whenCreateUserThenReturnCreated() throws Exception {

        json = getJsonOf(UserAppMock.getRequest());

        mockMvc.perform(
                        post(API_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content(json)
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    void whenCreateUserThenReturnConflict() throws Exception {

        json = getJsonOf(UserAppMock.getRequest());

        mockMvc.perform(
                        post(API_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content(json)
                )
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void whenCreateUserThenReturnBadRequest() throws Exception {

        json = getJsonOf(new UserRequest());

        mockMvc.perform(
                        post(API_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content(json)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenFindAllUsersThenReturnForbidden() throws Exception {

        mockMvc.perform(
                        get(API_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void whenFindAllUsersThenReturnList() throws Exception {

        mockMvc.perform(
                        get(API_URL)
                                .header(HttpHeaders.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void whenUpdateUserThenReturnForbidden() throws Exception {

        json = getJsonOf(UserAppMock.getRequestToUpdate());

        mockMvc.perform(
                        put(API_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content(json)
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(3)
    void whenUpdateUserThenReturnSuccess() throws Exception {

        json = getJsonOf(UserAppMock.getRequestToUpdate());

        mockMvc.perform(
                        put(API_URL)
                                .header(HttpHeaders.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content(json)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void whenUpdateUserThenReturnBadRequest() throws Exception {

        UserRequest requestToUpdate = new UserRequest();
        requestToUpdate.setId(UserAppMock.ID);
        json = getJsonOf(requestToUpdate);

        mockMvc.perform(
                        put(API_URL)
                                .header(HttpHeaders.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content(json)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(4)
    void whenUpdateUserThenReturnNotFound() throws Exception {
        UserRequest requestToUpdate = UserAppMock.getRequestToUpdate();
        requestToUpdate.setId(2L);

        json = getJsonOf(requestToUpdate);

        mockMvc.perform(
                        put(API_URL)
                                .header(HttpHeaders.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content(json)
                )
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @Order(5)
    void whenUpdateUserThenReturnAlreadyExists() throws Exception {
        UserRequest requestToUpdate = UserAppMock.getRequestToUpdate();
        requestToUpdate.setUsername(UserAppMock.USERNAME_ALREADY_EXISTS);
        requestToUpdate.setId(3L);

        json = getJsonOf(requestToUpdate);

        mockMvc.perform(
                        put(API_URL)
                                .header(HttpHeaders.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content(json)
                )
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @Order(6)
    void whenDeleteUserThenReturnSuccess() throws Exception {

        mockMvc.perform(
                        delete(API_URL + "/3")
                                .header(HttpHeaders.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void whenDeleteUserThenReturnBadRequest() throws Exception {

        mockMvc.perform(
                        delete(API_URL + "/null")
                                .header(HttpHeaders.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenDeleteUserThenReturnNotFound() throws Exception {

        mockMvc.perform(
                        delete(API_URL + "/999")
                                .header(HttpHeaders.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void whenEnableUserThenReturnSuccess() throws Exception {

        mockMvc.perform(
                        put(API_URL + "/enable/1")
                                .header(HttpHeaders.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content(json)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void whenEnableUserThenReturnNotFound() throws Exception {

        mockMvc.perform(
                        put(API_URL + "/enable/999")
                                .header(HttpHeaders.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content(json)
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void whenEnableUserThenReturnBadRequest() throws Exception {

        mockMvc.perform(
                        put(API_URL + "/enable/null")
                                .header(HttpHeaders.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content(json)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    void whenDisableUserThenReturnSuccess() throws Exception {

        mockMvc.perform(
                        put(API_URL + "/disable/1")
                                .header(HttpHeaders.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content(json)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void whenDisableUserThenReturnNotFound() throws Exception {

        mockMvc.perform(
                        put(API_URL + "/disable/999")
                                .header(HttpHeaders.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content(json)
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void whenDisableUserThenReturnBadRequest() throws Exception {

        mockMvc.perform(
                        put(API_URL + "/disable/null")
                                .header(HttpHeaders.AUTHORIZATION, token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .content(json)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    private void createInstances() throws JsonProcessingException {
        token = "Bearer " + tokenService.getToken(UserAppMock.getAdminUser());
        json = getJsonOf(UserAppMock.getRequest());
    }

    private String getJsonOf(Object o) throws JsonProcessingException {
        return objectMapper.writeValueAsString(o);
    }


}