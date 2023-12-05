package br.com.rsanme.controlegastos.services.impl;

import br.com.rsanme.controlegastos.exceptions.CustomEntityAlreadyExistsException;
import br.com.rsanme.controlegastos.exceptions.CustomEntityNotFoundException;
import br.com.rsanme.controlegastos.models.TipoPagamento;
import br.com.rsanme.controlegastos.repositories.TipoPagamentoRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 09/11/2023
 * Hora: 17:11
 */
@ExtendWith(MockitoExtension.class)
class TipoPagamentoServiceImplTest {

    public static final Long ID = 1L;
    public static final String TIPO_PAGAMENTO_DINHEIRO = "Dinheiro";
    @InjectMocks
    private TipoPagamentoServiceImpl service;

    private TipoPagamento tipoPagamento;

    @Mock
    private TipoPagamentoRepository repository;

    @BeforeEach
    void setUp() {
        createInstances();
    }


    @Test
    void whenFindAllThenReturnList() {
        List<TipoPagamento> list = List.of(tipoPagamento);

        when(repository.findAll()).thenReturn(list);

        List<TipoPagamento> response = service.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(TipoPagamento.class, response.get(0).getClass());
        assertEquals(tipoPagamento, response.get(0));

        verify(repository)
                .findAll();
    }

    @Test
    void whenFindByIdThenSuccess() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(tipoPagamento));

        TipoPagamento response = service.findById(ID);

        assertNotNull(response);
        assertEquals(TipoPagamento.class, response.getClass());
        assertEquals(TIPO_PAGAMENTO_DINHEIRO, response.getTipo());
        assertEquals(ID, response.getId());

        verify(repository)
                .findById(anyLong());
    }

    @Test
    void whenFindByIdThenThrowsNotFound() {

        String errorMessage = String.format("Tipo de Pagamento com Id %s não encontrado!", ID);

        assertThatThrownBy(() -> service.findById(ID))
                .hasMessage(errorMessage)
                .isInstanceOf(CustomEntityNotFoundException.class);

        verify(repository)
                .findById(anyLong());
    }

    @Test
    void whenCreateThenReturnSuccess() {
        TipoPagamento toSave = new TipoPagamento();
        toSave.setTipo(TIPO_PAGAMENTO_DINHEIRO);

        when(repository.findByTipo(anyString())).thenReturn(Optional.empty());
        when(repository.save(any())).thenReturn(tipoPagamento);

        TipoPagamento response = service.create(toSave);

        assertNotNull(response);
        assertEquals(TipoPagamento.class, response.getClass());
        assertEquals(tipoPagamento.getId(), response.getId());
        assertEquals(tipoPagamento.getTipo(), response.getTipo());

        assertEquals(tipoPagamento.hashCode(), response.hashCode());
        assertTrue(tipoPagamento.equals(response));
        assertFalse(tipoPagamento.equals(null));
        assertFalse(tipoPagamento.equals(toSave));
        assertTrue(tipoPagamento.toString().contains("TipoPagamento"));

        verify(repository)
                .save(any());
    }

    @Test
    void whenCreateThenThrowsException() {

        TipoPagamento paraSalvar = new TipoPagamento();
        paraSalvar.setTipo(TIPO_PAGAMENTO_DINHEIRO);

        String errorMessage = String.format(
                "Um Tipo de Pagamento com o tipo %s já existe!", TIPO_PAGAMENTO_DINHEIRO);

        when(repository.findByTipo(anyString())).thenReturn(Optional.of(tipoPagamento));

        assertThatThrownBy(() -> service.create(paraSalvar))
                .hasMessage(errorMessage)
                .isInstanceOf(CustomEntityAlreadyExistsException.class);

        verify(repository)
                .findByTipo(TIPO_PAGAMENTO_DINHEIRO);

        verify(repository, never())
                .save(any());
    }

    @Test
    void whenUpdateThenReturnSuccess() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(tipoPagamento));
        when(repository.findByTipo(anyString())).thenReturn(Optional.of(tipoPagamento));
        when(repository.save(any())).thenReturn(tipoPagamento);

        TipoPagamento response = service.update(tipoPagamento);

        assertNotNull(response);
        assertEquals(TipoPagamento.class, response.getClass());
        assertEquals(tipoPagamento.getId(), response.getId());
        assertEquals(tipoPagamento.getTipo(), response.getTipo());

        verify(repository)
                .save(any());
    }

    @Test
    void whenUpdateThenThrowsNotFound() {

        String errorMessage = String.format("Tipo de Pagamento com Id %s não encontrado!", ID);

        assertThatThrownBy(() -> service.update(tipoPagamento))
                .hasMessage(errorMessage)
                .isInstanceOf(CustomEntityNotFoundException.class);

        verify(repository)
                .findById(anyLong());

        verify(repository, never())
                .save(any());
    }

    @Test
    void whenUpdateThenThrowsAlreadyExists() {

        TipoPagamento paraAtualizar = new TipoPagamento();
        paraAtualizar.setId(2L);
        paraAtualizar.setTipo(TIPO_PAGAMENTO_DINHEIRO);

        String errorMessage = String.format(
                "Um Tipo de Pagamento com o tipo %s já existe!", TIPO_PAGAMENTO_DINHEIRO);

        when(repository.findById(anyLong())).thenReturn(Optional.of(tipoPagamento));
        when(repository.findByTipo(anyString())).thenReturn(Optional.of(tipoPagamento));

        assertThatThrownBy(() -> service.update(paraAtualizar))
                .hasMessage(errorMessage)
                .isInstanceOf(CustomEntityAlreadyExistsException.class);

        verify(repository)
                .findByTipo(TIPO_PAGAMENTO_DINHEIRO);

        verify(repository, never())
                .save(any());
    }

    @Test
    void whenDeleteThenSuccess() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(tipoPagamento));

        service.delete(ID);

        verify(repository)
                .findById(anyLong());

        verify(repository)
                .deleteById(anyLong());
    }

    @Test
    void whenDeleteThenThrowsNotFound() {

        String errorMessage = String.format("Tipo de Pagamento com Id %s não encontrado!", ID);

        assertThatThrownBy(() -> service.delete(ID))
                .hasMessage(errorMessage)
                .isInstanceOf(CustomEntityNotFoundException.class);

        verify(repository)
                .findById(anyLong());

        verify(repository, never())
                .deleteById(anyLong());
    }

    private void createInstances() {
        tipoPagamento = new TipoPagamento(1L, TIPO_PAGAMENTO_DINHEIRO);
    }
}