package br.com.rsanme.controlegastos.auth.domain.controllers;

import br.com.rsanme.controlegastos.auth.domain.dtos.UserRequest;
import br.com.rsanme.controlegastos.auth.domain.models.UserApp;
import br.com.rsanme.controlegastos.auth.domain.services.impl.UserAppService;
import br.com.rsanme.controlegastos.exceptions.CustomEntityAlreadyExistsException;
import br.com.rsanme.controlegastos.exceptions.CustomEntityNotFoundException;
import br.com.rsanme.controlegastos.exceptions.handlers.CustomApiExceptionHandler;
import br.com.rsanme.controlegastos.utils.UserAppMock;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 21/12/2023
 * Hora: 22:18
 */
@ExtendWith(MockitoExtension.class)
class UserAppControllerTest {

    public static final String API_URL = "/api/v1/users/";

    @Mock
    private UserAppService service;

    @InjectMocks
    private UserAppController controller;

    @InjectMocks
    private CustomApiExceptionHandler exceptionHandler;

    private UserApp userApp;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.standaloneSetup(controller, exceptionHandler);
        createInstances();
    }

    @Test
    void whenFindAllUsersThenReturnList() {

        Mockito.when(service.findAll()).thenReturn(List.of(userApp));

        given()
                .when()
                .get(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.OK.value());

        verify(service).findAll();
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenCreateUserThenReturnCreated() {

        when(service.create(any(UserApp.class))).thenReturn(userApp);

        given()
                .body(userApp)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .post(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(CREATED.value());

        verify(service).create(any(UserApp.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenCreateUserThenReturnBadRequest() {

        given()
                .body(new UserRequest())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .post(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(BAD_REQUEST.value());

        verify(service, never()).create(any(UserApp.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenCreateUserThenReturnAlreadyExists() {

        UserRequest toSave = UserAppMock.getRequest();
        toSave.setId(2L);

        when(service.create(any(UserApp.class)))
                .thenThrow(new CustomEntityAlreadyExistsException(UserAppMock.ERROR_MESSAGE_ALREADY_EXISTS));

        given()
                .body(toSave)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .post(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(CONFLICT.value());

        verify(service).create(any(UserApp.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenUpdateUserThenReturnBadRequest() {

        given()
                .body(new UserRequest())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .put(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(BAD_REQUEST.value());

        verify(service, never()).update(any(UserApp.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenUpdateUserThenReturnSuccess() {

        when(service.update(any(UserApp.class))).thenReturn(userApp);

        given()
                .body(UserAppMock.userAppToUpdate())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .put(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(OK.value());

        verify(service).update(any(UserApp.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenUpdateUserThenReturnAlreadyExists() {

        UserRequest toUpdate = UserAppMock.getRequestToUpdate();
        toUpdate.setId(2L);

        when(service.update(any(UserApp.class)))
                .thenThrow(new CustomEntityAlreadyExistsException(UserAppMock.ERROR_MESSAGE_ALREADY_EXISTS));

        given()
                .body(toUpdate)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .put(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(CONFLICT.value());

        verify(service).update(any(UserApp.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenUpdateUserThenReturnNotFound() {

        UserRequest toUpdate = UserAppMock.getRequestToUpdate();
        toUpdate.setId(2L);

        when(service.update(any(UserApp.class)))
                .thenThrow(new CustomEntityNotFoundException(UserAppMock.ERROR_MESSAGE_NOT_FOUND));

        given()
                .body(toUpdate)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .put(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(NOT_FOUND.value());

        verify(service).update(any(UserApp.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenDeleteUserThenReturnSuccess() {

        given()
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .delete(API_URL + UserAppMock.ID)
                .then()
                .log().ifValidationFails()
                .statusCode(OK.value());

        verify(service).delete(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenDeleteUserThenReturnBadRequest() {

        given()
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .delete(API_URL + null)
                .then()
                .log().ifValidationFails()
                .statusCode(BAD_REQUEST.value());

        verify(service, never()).delete(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenEnableUserThenReturnSuccess() {

        String enableUrl = API_URL + "enable/" + UserAppMock.ID;

        given()
                .body(UserAppMock.userAppToUpdate())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .put(enableUrl)
                .then()
                .log().ifValidationFails()
                .statusCode(OK.value());

        verify(service).enableUser(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenEnableUserThenReturnBadRequest() {

        String enableUrl = API_URL + "enable/" + null;

        given()
                .body(UserAppMock.userAppToUpdate())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .put(enableUrl)
                .then()
                .log().ifValidationFails()
                .statusCode(BAD_REQUEST.value());

        verify(service, never()).enableUser(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenDisableUserThenReturnSuccess() {

        String enableUrl = API_URL + "disable/" + UserAppMock.ID;

        given()
                .body(UserAppMock.userAppToUpdate())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .put(enableUrl)
                .then()
                .log().ifValidationFails()
                .statusCode(OK.value());

        verify(service).disableUser(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenDisableUserThenReturnBadRequest() {

        String enableUrl = API_URL + "disable/" + null;

        given()
                .body(UserAppMock.userAppToUpdate())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .put(enableUrl)
                .then()
                .log().ifValidationFails()
                .statusCode(BAD_REQUEST.value());

        verify(service, never()).disableUser(anyLong());
        verifyNoMoreInteractions(service);
    }

    private void createInstances() {

        userApp = UserAppMock.getUserApp();
    }

}