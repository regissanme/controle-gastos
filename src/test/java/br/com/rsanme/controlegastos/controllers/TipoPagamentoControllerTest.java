package br.com.rsanme.controlegastos.controllers;

import br.com.rsanme.controlegastos.exceptions.handlers.CustomApiExceptionHandler;
import br.com.rsanme.controlegastos.services.impl.TipoPagamentoService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 06/12/2023
 * Hora: 15:25
 */

@ExtendWith(MockitoExtension.class)
class TipoPagamentoControllerTest {

    @Mock
    private TipoPagamentoService service;

    @InjectMocks
    private TipoPagamentoController controller;

    private CustomApiExceptionHandler exceptionHandler;



    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.standaloneSetup(controller, exceptionHandler);
        createInstances();
    }

    private void createInstances(){

    }
}