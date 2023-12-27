package br.com.rsanme.controlegastos.controllers;

import br.com.rsanme.controlegastos.dtos.DespesaRequest;
import br.com.rsanme.controlegastos.exceptions.BusinessException;
import br.com.rsanme.controlegastos.exceptions.CustomEntityNotFoundException;
import br.com.rsanme.controlegastos.exceptions.handlers.CustomApiExceptionHandler;
import br.com.rsanme.controlegastos.models.Despesa;
import br.com.rsanme.controlegastos.services.impl.DespesaService;
import br.com.rsanme.controlegastos.utils.DespesaMock;
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
 * Data: 12/12/2023
 * Hora: 16:09
 */
@ExtendWith(MockitoExtension.class)
class DespesaControllerTest {

    public static final String API_URL = "/api/v1/despesa/";

    @Mock
    private DespesaService service;

    @InjectMocks
    private DespesaController controller;

    @InjectMocks
    private CustomApiExceptionHandler exceptionHandler;

    private Despesa despesa;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.standaloneSetup(controller, exceptionHandler);
        despesa = DespesaMock.getDespesa();
    }

    @Test
    void whenFindAllExpanseByUserIdThenReturnList() {

        when(service.findAllByUser(anyLong())).thenReturn(List.of(despesa));

        given()
                .when()
                .get(API_URL + "/all/1")
                .then()
                .log().ifValidationFails()
                .statusCode(OK.value());

        verify(service).findAllByUser(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenFindAllExpanseByUserIdThenReturnBadRequest() {

        when(service.findAllByUser(0L))
                .thenThrow(new BusinessException(DespesaMock.ERROR_MESSAGE_BUSINESS_EXCEPTION));

        given()
                .when()
                .get(API_URL + "/all/0")
                .then()
                .log().ifValidationFails()
                .statusCode(BAD_REQUEST.value());

        verify(service).findAllByUser(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenFindByIdExpanseThenReturnSuccess() {

        when(service.findById(anyLong())).thenReturn(despesa);

        given()
                .when()
                .get(API_URL + DespesaMock.ID)
                .then()
                .log().ifValidationFails()
                .statusCode(OK.value());

        verify(service).findById(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenFindByIdExpanseThenReturnNotFound() {

        when(service.findById(anyLong()))
                .thenThrow(new CustomEntityNotFoundException(DespesaMock.ERROR_MESSAGE_NOT_FOUND));

        given()
                .when()
                .get(API_URL + DespesaMock.ID)
                .then()
                .log().ifValidationFails()
                .statusCode(NOT_FOUND.value());

        verify(service).findById(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenFindByIdExpanseThenReturnBadRequest() {

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
    void whenCreateExpanseThenReturnCreated() {

        when(service.create(any(Despesa.class))).thenReturn(despesa);

        given()
                .body(DespesaMock.getDespesaRequestToSave())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .post(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(CREATED.value());

        verify(service).create(any(Despesa.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenCreateExpanseThenReturnBadRequest() {

        given()
                .body("{}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .post(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(BAD_REQUEST.value());

        verify(service, never()).create(any(Despesa.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenUpdateExpanseThenReturnBadRequest() {

        given()
                .body(new Despesa())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .put(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(BAD_REQUEST.value());

        verify(service, never()).update(any(Despesa.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenUpdateExpanseThenReturnSuccess() {

        when(service.update(any(Despesa.class))).thenReturn(despesa);

        given()
                .body(DespesaMock.getDespesaRequestToUpdate())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .put(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(OK.value());

        verify(service).update(any(Despesa.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenUpdateExpanseThenReturnNotFound() {

        DespesaRequest toUpdate = DespesaMock.getDespesaRequestToUpdate();
        toUpdate.setId(2L);

        when(service.update(any(Despesa.class)))
                .thenThrow(new CustomEntityNotFoundException(DespesaMock.ERROR_MESSAGE_NOT_FOUND));

        given()
                .body(toUpdate)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .put(API_URL)
                .then()
                .log().ifValidationFails()
                .statusCode(NOT_FOUND.value());

        verify(service).update(any(Despesa.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenDeleteExpanseThenReturnSuccess() {

        given()
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .delete(API_URL + DespesaMock.ID)
                .then()
                .log().ifValidationFails()
                .statusCode(OK.value());

        verify(service).delete(anyLong());
        verifyNoMoreInteractions(service);
    }

    @Test
    void whenDeleteExpanseThenReturnBadRequest() {

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