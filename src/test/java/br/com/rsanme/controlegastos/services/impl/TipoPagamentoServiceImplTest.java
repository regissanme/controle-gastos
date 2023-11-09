package br.com.rsanme.controlegastos.services.impl;

import br.com.rsanme.controlegastos.models.TipoPagamento;
import br.com.rsanme.controlegastos.repositories.TipoPagamentoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 09/11/2023
 * Hora: 17:11
 */
@ExtendWith(MockitoExtension.class)
class TipoPagamentoServiceImplTest {

    @InjectMocks
    private TipoPagamentoServiceImpl service;

    private TipoPagamento tipoPagamento;
    private Optional<TipoPagamento> optionalTipoPagamento;

    @Mock
    private TipoPagamentoRepository repository;

    @BeforeEach
    void setUp() {
        createInstances();
    }



    @Test
    void whenFindAllThenReturnList() {
        List<TipoPagamento> list = List.of(tipoPagamento);

        Mockito.when(repository.findAll()).thenReturn(list);

        List<TipoPagamento> response = service.findAll();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals(TipoPagamento.class, response.get(0).getClass());
        Assertions.assertEquals(tipoPagamento, response.get(0));
    }

    @Test
    void findById() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    private void createInstances() {
        tipoPagamento = new TipoPagamento(1L, "Dinheiro");
        optionalTipoPagamento = Optional.of(tipoPagamento);
    }
}