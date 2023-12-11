package br.com.rsanme.controlegastos.controllers;

import br.com.rsanme.controlegastos.exceptions.CustomEntityAlreadyExistsException;
import br.com.rsanme.controlegastos.exceptions.CustomEntityNotFoundException;
import br.com.rsanme.controlegastos.exceptions.handlers.CustomApiExceptionHandler;
import br.com.rsanme.controlegastos.models.TipoPagamento;
import br.com.rsanme.controlegastos.services.impl.TipoPagamentoService;
import br.com.rsanme.controlegastos.utils.TipoPagamentoMock;
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

    public static final String API_URL = "/api/v1/pagamento/tipo/";

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
    void whenFindAllPaymentTypeThenReturnList() {

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
    void whenFindByIdPaymentTypeThenReturnSuccess() {

        when(service.findById(anyLong())).thenReturn(tipoPagamento);

        given()
                .when()
                .get(API_URL + TipoPagamentoMock.ID)
                .then()
                .log().ifValidationFails()
                .statusCode(OK.value());

        verify(service).findById(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenFindByIdPaymentTypeThenReturnNotFound() {

        when(service.findById(anyLong()))
                .thenThrow(new CustomEntityNotFoundException(TipoPagamentoMock.ERROR_MESSAGE_NOT_FOUND));

        given()
                .when()
                .get(API_URL + TipoPagamentoMock.ID)
                .then()
                .log().ifValidationFails()
                .statusCode(NOT_FOUND.value());

        verify(service).findById(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenFindByIdPaymentTypeThenReturnBadRequest() {

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
    void whenCreatePaymentTypeThenReturnCreated() {

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
    void whenCreatePaymentTypeThenReturnBadRequest() {

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
    void whenCreatePaymentTypeThenReturnAlreadyExists() {

        TipoPagamento toSave = TipoPagamentoMock.getTipoPagamentoToSave();
        toSave.setId(2L);

        when(service.create(any(TipoPagamento.class)))
                .thenThrow(new CustomEntityAlreadyExistsException(TipoPagamentoMock.ERROR_MESSAGE_ALREADY_EXISTS));

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
    void whenUpdatePaymentTypeThenReturnBadRequest() {

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
    void whenUpdatePaymentTypeThenReturnSuccess() {

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
    void whenUpdatePaymentTypeThenReturnAlreadyExists() {

        TipoPagamento toUpdate = TipoPagamentoMock.getTipoPagamentoToUpdate();
        toUpdate.setId(2L);

        when(service.update(any(TipoPagamento.class)))
                .thenThrow(new CustomEntityAlreadyExistsException(TipoPagamentoMock.ERROR_MESSAGE_ALREADY_EXISTS));

        given()
                .body(toUpdate)
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
    void whenUpdatePaymentTypeThenReturnNotFound() {

        TipoPagamento toUpdate = TipoPagamentoMock.getTipoPagamentoToUpdate();
        toUpdate.setId(2L);

        when(service.update(any(TipoPagamento.class)))
                .thenThrow(new CustomEntityNotFoundException(TipoPagamentoMock.ERROR_MESSAGE_NOT_FOUND));

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
    void whenDeletePaymentTypeThenReturnSuccess() {

        given()
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .delete(API_URL + TipoPagamentoMock.ID)
                .then()
                .log().ifValidationFails()
                .statusCode(OK.value());

        verify(service).delete(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenDeletePaymentTypeThenReturnBadRequest() {

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

        tipoPagamento = TipoPagamentoMock.getTipoPagamento();
    }
}