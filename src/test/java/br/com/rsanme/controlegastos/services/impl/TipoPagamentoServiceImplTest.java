package br.com.rsanme.controlegastos.services.impl;

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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

        when(repository.findAll()).thenReturn(list);

        List<TipoPagamento> response = service.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(TipoPagamento.class, response.get(0).getClass());
        assertEquals(tipoPagamento, response.get(0));

        verify(repository, times(1))
                .findAll();
    }

    @Test
    void whenFindByIdThenSuccess() {

        when(repository.findById(anyLong())).thenReturn(optionalTipoPagamento);

        TipoPagamento response = service.findById(ID);

        assertNotNull(response);
        assertEquals(TipoPagamento.class, response.getClass());
        assertEquals(TIPO_PAGAMENTO_DINHEIRO, response.getTipo());
        assertEquals(ID, response.getId());

        verify(repository, times(1))
                .findById(anyLong());
    }

    @Test
    void whenFindByIdThenThrowsNotFound() {

        String errorMessage = String.format("Tipo de Pagamento com Id %s não encontrado!", ID);

        assertThatThrownBy(() -> service.findById(ID))
                .hasMessage(errorMessage)
                .isInstanceOf(EntityNotFoundException.class);

        verify(repository, times(1))
                .findById(anyLong());
    }

    @Test
    void whenCreateThenReturnSuccess() {
        TipoPagamento paraSalvar = new TipoPagamento();
        paraSalvar.setTipo(TIPO_PAGAMENTO_DINHEIRO);

        when(repository.findByTipo(anyString())).thenReturn(Optional.empty());
        when(repository.save(any())).thenReturn(tipoPagamento);

        TipoPagamento response = service.create(paraSalvar);

        assertNotNull(response);
        assertEquals(TipoPagamento.class, response.getClass());
        assertEquals(tipoPagamento.getId(), response.getId());
        assertEquals(tipoPagamento.getTipo(), response.getTipo());

        verify(repository, times(1))
                .save(any());
    }

    @Test
    void whenCreateThenThrowsException() {

        TipoPagamento paraSalvar = new TipoPagamento();
        paraSalvar.setTipo(TIPO_PAGAMENTO_DINHEIRO);

        String errorMessage = String.format(
                "Um Tipo de Pagamento com o tipo %s já existe!", TIPO_PAGAMENTO_DINHEIRO);

        when(repository.findByTipo(anyString())).thenReturn(optionalTipoPagamento);

        assertThatThrownBy(() -> service.create(paraSalvar))
                .hasMessage(errorMessage)
                .isInstanceOf(EntityExistsException.class);

        verify(repository, times(1))
                .findByTipo(TIPO_PAGAMENTO_DINHEIRO);

        verify(repository, never())
                .save(any());
    }

    @Test
    void whenUpdateThenReturnSuccess() {

        when(repository.findById(anyLong())).thenReturn(optionalTipoPagamento);
        when(repository.findByTipo(anyString())).thenReturn(optionalTipoPagamento);
        when(repository.save(any())).thenReturn(tipoPagamento);

        TipoPagamento response = service.update(tipoPagamento);

        assertNotNull(response);
        assertEquals(TipoPagamento.class, response.getClass());
        assertEquals(tipoPagamento.getId(), response.getId());
        assertEquals(tipoPagamento.getTipo(), response.getTipo());

        verify(repository, times(1))
                .save(any());
    }

    @Test
    void whenUpdateThenThrowsNotFound() {

        String errorMessage = String.format("Tipo de Pagamento com Id %s não encontrado!", ID);

        assertThatThrownBy(() -> service.update(tipoPagamento))
                .hasMessage(errorMessage)
                .isInstanceOf(EntityNotFoundException.class);

        verify(repository, times(1))
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

        when(repository.findById(anyLong())).thenReturn(optionalTipoPagamento);
        when(repository.findByTipo(anyString())).thenReturn(optionalTipoPagamento);

        assertThatThrownBy(() -> service.update(paraAtualizar))
                .hasMessage(errorMessage)
                .isInstanceOf(EntityExistsException.class);

        verify(repository, times(1))
                .findByTipo(TIPO_PAGAMENTO_DINHEIRO);

        verify(repository, never())
                .save(any());
    }

    @Test
    void whenDeleteThenSuccess() {

        when(repository.findById(anyLong())).thenReturn(optionalTipoPagamento);

        service.delete(ID);

        verify(repository, times(1))
                .findById(anyLong());

        verify(repository, times(1))
                .deleteById(anyLong());

    }

    @Test
    void whenDeleteThenThrowsNotFound() {

        String errorMessage = String.format("Tipo de Pagamento com Id %s não encontrado!", ID);

        assertThatThrownBy(() -> service.delete(ID))
                .hasMessage(errorMessage)
                .isInstanceOf(EntityNotFoundException.class);

        verify(repository, times(1))
                .findById(anyLong());

        verify(repository, never())
                .deleteById(anyLong());
    }

    private void createInstances() {
        tipoPagamento = new TipoPagamento(1L, TIPO_PAGAMENTO_DINHEIRO);
        optionalTipoPagamento = Optional.of(tipoPagamento);
    }
}