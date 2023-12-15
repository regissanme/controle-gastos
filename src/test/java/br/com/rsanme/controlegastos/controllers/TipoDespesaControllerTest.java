package br.com.rsanme.controlegastos.controllers;

import br.com.rsanme.controlegastos.dtos.TipoDespesaRequest;
import br.com.rsanme.controlegastos.exceptions.CustomEntityAlreadyExistsException;
import br.com.rsanme.controlegastos.exceptions.CustomEntityNotFoundException;
import br.com.rsanme.controlegastos.exceptions.handlers.CustomApiExceptionHandler;
import br.com.rsanme.controlegastos.models.TipoDespesa;
import br.com.rsanme.controlegastos.services.impl.TipoDespesaService;
import br.com.rsanme.controlegastos.utils.TipoDespesaMock;
import io.restassured.http.ContentType;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 07/12/2023
 * Hora: 23:03
 */
@ExtendWith(MockitoExtension.class)
class TipoDespesaControllerTest {

    public static final String API_URL = "/api/v1/despesa/tipo/";

    @Mock
    private TipoDespesaService service;

    @InjectMocks
    private TipoDespesaController controller;

    @InjectMocks
    private CustomApiExceptionHandler exceptionHandler;

    private TipoDespesa tipoDespesa;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.standaloneSetup(controller, exceptionHandler);
        createInstances();
    }

    @Test
    void whenFindAllExpensiveTypeThenReturnList() {

        when(service.findAll()).thenReturn(List.of(tipoDespesa));

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

        when(service.findById(anyLong())).thenReturn(tipoDespesa);

        given()
                .when()
                .get(API_URL + TipoDespesaMock.ID)
                .then()
                .log().ifValidationFails()
                .statusCode(OK.value());

        verify(service).findById(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenFindByIdExpensiveTypeThenReturnNotFound() {

        when(service.findById(anyLong()))
                .thenThrow(new CustomEntityNotFoundException(TipoDespesaMock.ERROR_MESSAGE_NOT_FOUND));

        given()
                .when()
                .get(API_URL + TipoDespesaMock.ID)
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

        when(service.create(any(TipoDespesa.class))).thenReturn(tipoDespesa);

        given()
                .body(TipoDespesaMock.getTipoDespesaRequestToSave())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(ContentType.JSON)
                .when()
                .post(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(CREATED.value());

        verify(service).create(any(TipoDespesa.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenCreateExpensiveTypeThenReturnBadRequest() {

        given()
                .body(new TipoDespesa())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .post(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(BAD_REQUEST.value());

        verify(service, never()).create(any(TipoDespesa.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenCreateExpensiveTypeThenReturnAlreadyExists() {

        TipoDespesaRequest toSave = TipoDespesaMock.getTipoDespesaRequestToSave();
        toSave.setId(2L);

        when(service.create(any(TipoDespesa.class)))
                .thenThrow(new CustomEntityAlreadyExistsException(TipoDespesaMock.ERROR_MESSAGE_ALREADY_EXISTS));

        given()
                .body(toSave)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .post(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(CONFLICT.value());

        verify(service).create(any(TipoDespesa.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenUpdateExpensiveTypeThenReturnBadRequest() {

        given()
                .body(new TipoDespesa())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .put(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(BAD_REQUEST.value());

        verify(service, never()).update(any(TipoDespesa.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenUpdateExpensiveTypeThenReturnSuccess() {

        when(service.update(any(TipoDespesa.class))).thenReturn(tipoDespesa);

        given()
                .body(TipoDespesaMock.getTipoDespesaRequestToUpdate())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .put(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(OK.value());

        verify(service).update(any(TipoDespesa.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenUpdateExpensiveTypeThenReturnAlreadyExists() {

        TipoDespesaRequest toUpdate = TipoDespesaMock.getTipoDespesaRequestToUpdate();
        toUpdate.setId(2L);

        when(service.update(any(TipoDespesa.class)))
                .thenThrow(new CustomEntityAlreadyExistsException(TipoDespesaMock.ERROR_MESSAGE_ALREADY_EXISTS));

        given()
                .body(toUpdate)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .put(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(CONFLICT.value());

        verify(service).update(any(TipoDespesa.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenUpdateExpensiveTypeThenReturnNotFound() {

        TipoDespesaRequest toUpdate = TipoDespesaMock.getTipoDespesaRequestToUpdate();
        toUpdate.setId(2L);
        System.out.println(toUpdate.toString());

        when(service.update(any(TipoDespesa.class)))
                .thenThrow(new CustomEntityNotFoundException(TipoDespesaMock.ERROR_MESSAGE_NOT_FOUND));

        given()
                .body(toUpdate)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .put(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(NOT_FOUND.value());

        verify(service).update(any(TipoDespesa.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenDeleteExpensiveTypeThenReturnSuccess() {

        given()
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .delete(API_URL + TipoDespesaMock.ID)
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
        tipoDespesa = TipoDespesaMock.getTipoDespesa();
    }
}