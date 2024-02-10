package br.com.rsanme.controlegastos.services.impl;

import br.com.rsanme.controlegastos.exceptions.BusinessException;
import br.com.rsanme.controlegastos.exceptions.CustomEntityNotFoundException;
import br.com.rsanme.controlegastos.models.Despesa;
import br.com.rsanme.controlegastos.models.TipoDespesa;
import br.com.rsanme.controlegastos.models.TipoPagamento;
import br.com.rsanme.controlegastos.repositories.DespesaRepository;
import br.com.rsanme.controlegastos.utils.DespesaMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 05/12/2023
 * Hora: 15:26
 */
@ExtendWith(MockitoExtension.class)
class DespesaServiceTest {

    @InjectMocks
    private DespesaService service;

    @Mock
    private DespesaRepository repository;

    private Despesa despesa;

    @BeforeEach
    void setUp() {
        createInstances();
    }

    @Test
    void whenFindAllByUserIdThenReturnList() {

        when(repository.findAllByUserId(anyLong())).thenReturn(List.of(despesa));

        List<Despesa> listResponse = service.findAllByUser(1L);

        assertNotNull(listResponse);
        assertEquals(1, listResponse.size());
        assertEquals(Despesa.class, listResponse.get(0).getClass());

        verify(repository).findAllByUserId(anyLong());
    }

    @Test
    void whenFindAllByUserIdThenThrowsBusinessException() {

        assertThatThrownBy(() -> service.findAllByUser(0L))
                .hasMessage(DespesaMock.ERROR_MESSAGE_BUSINESS_EXCEPTION)
                .isInstanceOf(BusinessException.class);

        verify(repository, never()).findAllByUserId(anyLong());
    }

    @Test
    void whenFindAllThenReturnList() {

        List<Despesa> listResponse = service.findAll();

        assertNotNull(listResponse);
        assertEquals(0, listResponse.size());

        verify(repository, never()).findAll();
    }

    @Test
    void whenFindByIdThenReturnInstance() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(despesa));

        Despesa response = service.findById(DespesaMock.ID);

        assertNotNull(response);
        assertEquals(DespesaMock.ID, response.getId());
        assertEquals(DespesaMock.DATA, response.getData());
        assertEquals(DespesaMock.DESCRICAO_DESPESA, response.getDescricao());
        assertEquals(DespesaMock.VALOR, response.getValor());
        assertEquals(TipoDespesa.class, response.getTipoDespesa().getClass());
        assertEquals(TipoPagamento.class, response.getTipoPagamento().getClass());

        verify(repository).findById(anyLong());
    }

    @Test
    void whenFindByIdThenThrowsNotFound() {

        assertThatThrownBy(() -> service.findById(DespesaMock.ID))
                .hasMessage(DespesaMock.ERROR_MESSAGE_NOT_FOUND)
                .isInstanceOf(CustomEntityNotFoundException.class);

        verify(repository).findById(anyLong());
    }

    @Test
    void whenCreateThenReturnInstance() {

        Despesa toSave = DespesaMock.getDespesaToSave();

        when(repository.save(any(Despesa.class))).thenReturn(despesa);

        Despesa response = service.create(toSave);

        assertNotNull(response);
        assertEquals(DespesaMock.ID, response.getId());
        assertEquals(DespesaMock.DATA, response.getData());
        assertEquals(DespesaMock.DESCRICAO_DESPESA, response.getDescricao());
        assertEquals(DespesaMock.VALOR, response.getValor());
        assertEquals(TipoDespesa.class, response.getTipoDespesa().getClass());
        assertEquals(TipoPagamento.class, response.getTipoPagamento().getClass());
        assertTrue(despesa.equals(response));
        assertFalse(despesa.equals(null));
        assertFalse(despesa.equals(toSave));
        assertEquals(despesa.hashCode(), response.hashCode());
        assertTrue(despesa.toString().contains("Despesa"));

        verify(repository).save(any(Despesa.class));
    }

    @Test
    void whenUpdateThenReturnInstance() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(despesa));
        when(repository.save(any(Despesa.class))).thenReturn(despesa);

        Despesa response = service.update(despesa);

        assertNotNull(response);

        verify(repository).findById(anyLong());
        verify(repository).save(any(Despesa.class));
    }

    @Test
    void whenUpdateThenThrowsNotFound() {

        assertThatThrownBy(() -> service.update(despesa))
                .hasMessage(DespesaMock.ERROR_MESSAGE_NOT_FOUND)
                .isInstanceOf(CustomEntityNotFoundException.class);

        verify(repository).findById(anyLong());
        verify(repository, never()).save(any(Despesa.class));
    }

    @Test
    void whenDeleteThenSuccess() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(despesa));

        service.delete(DespesaMock.ID);

        verify(repository).findById(anyLong());
        verify(repository).deleteById(anyLong());
    }

    @Test
    void whenDeleteThenThrowsNotFound() {

        assertThatThrownBy(() -> service.delete(DespesaMock.ID))
                .hasMessage(DespesaMock.ERROR_MESSAGE_NOT_FOUND)
                .isInstanceOf(CustomEntityNotFoundException.class);

        verify(repository).findById(anyLong());
        verify(repository, never()).deleteById(anyLong());
    }

    private void createInstances() {

        despesa = DespesaMock.getDespesa();

    }
}