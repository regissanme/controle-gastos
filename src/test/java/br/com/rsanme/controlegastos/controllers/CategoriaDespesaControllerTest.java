package br.com.rsanme.controlegastos.controllers;

import br.com.rsanme.controlegastos.exceptions.CustomEntityAlreadyExistsException;
import br.com.rsanme.controlegastos.exceptions.CustomEntityNotFoundException;
import br.com.rsanme.controlegastos.exceptions.handlers.CustomApiExceptionHandler;
import br.com.rsanme.controlegastos.models.CategoriaDespesa;
import br.com.rsanme.controlegastos.services.impl.CategoriaDespesaService;
import br.com.rsanme.controlegastos.utils.CategoriaDespesaMock;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
 * Data: 11/12/2023
 * Hora: 22:42
 */
@ExtendWith(MockitoExtension.class)
class CategoriaDespesaControllerTest {

    public static final String API_URL = "/api/v1/despesa/categoria/";

    @Mock
    private CategoriaDespesaService service;

    @InjectMocks
    private CategoriaDespesaController controller;

    @InjectMocks
    private CustomApiExceptionHandler exceptionHandler;

    private CategoriaDespesa categoriaDespesa;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.standaloneSetup(controller, exceptionHandler);
        categoriaDespesa = CategoriaDespesaMock.getCategoriaDespesa();
    }

    @Test
    void whenFindAllExpanseCategoryThenReturnList() {

        when(service.findAll()).thenReturn(List.of(categoriaDespesa));

        given()
                .when()
                .get(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(OK.value());

        verify(service).findAll();
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenFindByIdExpanseCategoryThenReturnSuccess() {

        when(service.findById(anyLong())).thenReturn(categoriaDespesa);

        given()
                .when()
                .get(API_URL + CategoriaDespesaMock.ID)
                .then()
                .log().ifValidationFails()
                .statusCode(OK.value());

        verify(service).findById(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenFindByIdExpanseCategoryThenReturnNotFound() {

        when(service.findById(anyLong()))
                .thenThrow(new CustomEntityNotFoundException(CategoriaDespesaMock.ERROR_MESSAGE_NOT_FOUND));

        given()
                .when()
                .get(API_URL + CategoriaDespesaMock.ID)
                .then()
                .log().ifValidationFails()
                .statusCode(NOT_FOUND.value());

        verify(service).findById(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenFindByIdExpanseCategoryThenReturnBadRequest() {

        given()
                .when()
                .get(API_URL + null)
                .then()
                .log().ifValidationFails()
                .statusCode(BAD_REQUEST.value());

        verify(service, never()).findById(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenCreateExpanseCategoryThenReturnCreated() {

        when(service.create(any(CategoriaDespesa.class))).thenReturn(categoriaDespesa);

        given()
                .body(categoriaDespesa)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .post(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(CREATED.value());

        verify(service).create(any(CategoriaDespesa.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenCreateExpanseCategoryThenReturnBadRequest() {

        given()
                .body(new CategoriaDespesa())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .post(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(BAD_REQUEST.value());

        verify(service, never()).create(any(CategoriaDespesa.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenCreateExpanseCategoryThenReturnAlreadyExists() {

        CategoriaDespesa toSave = CategoriaDespesaMock.getCategoriaDespesaToSave();
        toSave.setId(2L);

        when(service.create(any(CategoriaDespesa.class)))
                .thenThrow(new CustomEntityAlreadyExistsException(CategoriaDespesaMock.ERROR_MESSAGE_ALREADY_EXISTS));

        given()
                .body(toSave)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .post(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(CONFLICT.value());

        verify(service).create(any(CategoriaDespesa.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenUpdateExpanseCategoryThenReturnBadRequest() {

        given()
                .body(new CategoriaDespesa())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .put(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(BAD_REQUEST.value());

        verify(service, never()).update(any(CategoriaDespesa.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenUpdateExpanseCategoryThenReturnSuccess() {

        when(service.update(any(CategoriaDespesa.class))).thenReturn(categoriaDespesa);

        given()
                .body(categoriaDespesa)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .put(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(OK.value());

        verify(service).update(any(CategoriaDespesa.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenUpdateExpanseCategoryThenReturnAlreadyExists() {

        CategoriaDespesa toUpdate = CategoriaDespesaMock.getCategoriaDespesaToUpdate();
        toUpdate.setId(2L);

        when(service.update(any(CategoriaDespesa.class)))
                .thenThrow(new CustomEntityAlreadyExistsException(CategoriaDespesaMock.ERROR_MESSAGE_ALREADY_EXISTS));

        given()
                .body(toUpdate)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .put(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(CONFLICT.value());

        verify(service).update(any(CategoriaDespesa.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenUpdateExpanseCategoryThenReturnNotFound() {

        CategoriaDespesa toUpdate = CategoriaDespesaMock.getCategoriaDespesaToUpdate();
        toUpdate.setId(2L);

        when(service.update(any(CategoriaDespesa.class)))
                .thenThrow(new CustomEntityNotFoundException(CategoriaDespesaMock.ERROR_MESSAGE_NOT_FOUND));

        given()
                .body(toUpdate)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .put(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(NOT_FOUND.value());

        verify(service).update(any(CategoriaDespesa.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenDeleteExpanseCategoryThenReturnSuccess() {

        given()
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .delete(API_URL + CategoriaDespesaMock.ID)
                .then()
                .log().ifValidationFails()
                .statusCode(OK.value());

        verify(service).delete(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenDeleteExpanseCategoryThenReturnBadRequest() {

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

}