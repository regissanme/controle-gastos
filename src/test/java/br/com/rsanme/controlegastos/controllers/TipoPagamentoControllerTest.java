package br.com.rsanme.controlegastos.controllers;

import br.com.rsanme.controlegastos.exceptions.CustomEntityAlreadyExistsException;
import br.com.rsanme.controlegastos.exceptions.CustomEntityNotFoundException;
import br.com.rsanme.controlegastos.exceptions.handlers.CustomApiExceptionHandler;
import br.com.rsanme.controlegastos.models.TipoPagamento;
import br.com.rsanme.controlegastos.services.impl.TipoPagamentoService;
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
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 06/12/2023
 * Hora: 15:25
 */

@ExtendWith(MockitoExtension.class)
class TipoPagamentoControllerTest {

    public static final String API_URL = "/api/v1/tipo-pagamento/";

    public static final Long ID = 1L;
    public static final String EXPENSIVE_TYPE = "DINHEIRO";
    public static final String ERROR_MESSAGE_NOT_FOUND = "Tipo de Pagamento com Id " + ID + " não encontrado!";
    public static final String ERROR_MESSAGE_ALREADY_EXISTS = "Um Tipo de Pagamento com o tipo " + EXPENSIVE_TYPE + " já existe!";

    @Mock
    private TipoPagamentoService service;

    @InjectMocks
    private TipoPagamentoController controller;

    @InjectMocks
    private CustomApiExceptionHandler exceptionHandler;

    private TipoPagamento tipoPagamento;


    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.standaloneSetup(controller, exceptionHandler);
        createInstances();
    }


    @Test
    void whenFindAllExpensiveTypeThenReturnList() {

        when(service.findAll()).thenReturn(List.of(tipoPagamento));

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
    void whenFindByIdExpensiveTypeThenReturnSuccess() {

        when(service.findById(anyLong())).thenReturn(tipoPagamento);

        given()
                .when()
                .get(API_URL + ID)
                .then()
                .log().ifValidationFails()
                .statusCode(OK.value());

        verify(service).findById(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenFindByIdExpensiveTypeThenReturnNotFound() {

        when(service.findById(anyLong())).thenThrow(new CustomEntityNotFoundException(ERROR_MESSAGE_NOT_FOUND));

        given()
                .when()
                .get(API_URL + ID)
                .then()
                .log().ifValidationFails()
                .statusCode(NOT_FOUND.value());

        verify(service).findById(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenFindByIdExpensiveTypeThenReturnBadRequest() {

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
    void whenCreateExpensiveTypeThenReturnCreated() {

        when(service.create(any(TipoPagamento.class))).thenReturn(tipoPagamento);

        given()
                .body(tipoPagamento)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .post(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(CREATED.value());

        verify(service).create(any(TipoPagamento.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenCreateExpensiveTypeThenReturnBadRequest() {

        given()
                .body(new TipoPagamento())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .post(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(BAD_REQUEST.value());

        verify(service, never()).create(any(TipoPagamento.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenCreateExpensiveTypeThenReturnAlreadyExists() {

        TipoPagamento toSave = tipoPagamento;
        toSave.setId(2L);

        when(service.create(any(TipoPagamento.class)))
                .thenThrow(new CustomEntityAlreadyExistsException(ERROR_MESSAGE_ALREADY_EXISTS));

        given()
                .body(toSave)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .post(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(CONFLICT.value());

        verify(service).create(any(TipoPagamento.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenUpdateExpensiveTypeThenReturnBadRequest() {

        given()
                .body(new TipoPagamento())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .put(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(BAD_REQUEST.value());

        verify(service, never()).update(any(TipoPagamento.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenUpdateExpensiveTypeThenReturnSuccess() {

        when(service.update(any(TipoPagamento.class))).thenReturn(tipoPagamento);

        given()
                .body(tipoPagamento)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .put(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(OK.value());

        verify(service).update(any(TipoPagamento.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenUpdateExpensiveTypeThenReturnAlreadyExists() {

        TipoPagamento toSave = tipoPagamento;
        toSave.setId(2L);

        when(service.update(any(TipoPagamento.class)))
                .thenThrow(new CustomEntityAlreadyExistsException(ERROR_MESSAGE_ALREADY_EXISTS));

        given()
                .body(toSave)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .put(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(CONFLICT.value());

        verify(service).update(any(TipoPagamento.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenUpdateExpensiveTypeThenReturnNotFound() {

        TipoPagamento toUpdate = tipoPagamento;
        toUpdate.setId(2L);

        when(service.update(any(TipoPagamento.class)))
                .thenThrow(new CustomEntityNotFoundException(ERROR_MESSAGE_NOT_FOUND));

        given()
                .body(toUpdate)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .put(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(NOT_FOUND.value());

        verify(service).update(any(TipoPagamento.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenDeleteExpensiveTypeThenReturnSuccess() {

        given()
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .delete(API_URL + ID)
                .then()
                .log().ifValidationFails()
                .statusCode(OK.value());

        verify(service).delete(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenDeleteExpensiveTypeThenReturnBadRequest() {

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


    private void createInstances() {

        tipoPagamento = new TipoPagamento();
        tipoPagamento.setId(ID);
        tipoPagamento.setTipo(EXPENSIVE_TYPE);

    }
}