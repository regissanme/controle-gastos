package br.com.rsanme.controlegastos.services.impl;

import br.com.rsanme.controlegastos.exceptions.CustomEntityNotFoundException;
import br.com.rsanme.controlegastos.models.CategoriaDespesa;
import br.com.rsanme.controlegastos.models.Despesa;
import br.com.rsanme.controlegastos.models.TipoDespesa;
import br.com.rsanme.controlegastos.models.TipoPagamento;
import br.com.rsanme.controlegastos.repositories.DespesaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
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

    public static final Long ID = 1L;
    public static final String MES = "JANEIRO";
    public static final String DESCRICAO_DESPESA = "Gasto com combustível";
    public static final BigDecimal VALOR = new BigDecimal("200.54");
    public static final String ERRO_NOT_FOUND = "Despesa não encontrada com Id: " + ID;

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
    void whenFindAllThenReturnList() {

        when(repository.findAll()).thenReturn(List.of(despesa));

        List<Despesa> listResponse = service.findAll();

        assertNotNull(listResponse);
        assertEquals(1, listResponse.size());
        assertEquals(Despesa.class, listResponse.get(0).getClass());

        verify(repository).findAll();
    }

    @Test
    void whenFindByIdThenReturnInstance() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(despesa));

        Despesa response = service.findById(ID);

        assertNotNull(response);
        assertEquals(ID, response.getId());
        assertEquals(MES, response.getMes());
        assertEquals(DESCRICAO_DESPESA, response.getDescricao());
        assertEquals(VALOR, response.getValor());
        assertEquals(TipoDespesa.class, response.getTipoDespesa().getClass());
        assertEquals(TipoPagamento.class, response.getTipoPagamento().getClass());

        verify(repository).findById(anyLong());
    }

    @Test
    void whenFindByIdThenThrowsNotFound() {

        assertThatThrownBy(() -> service.findById(ID))
                .hasMessage(ERRO_NOT_FOUND)
                .isInstanceOf(CustomEntityNotFoundException.class);

        verify(repository).findById(anyLong());
    }

    @Test
    void whenCreateThenReturnInstance() {

        Despesa toSave = new Despesa(null, MES, VALOR, DESCRICAO_DESPESA, new TipoPagamento(), new TipoDespesa());

        when(repository.save(any(Despesa.class))).thenReturn(despesa);

        Despesa response = service.create(toSave);

        assertNotNull(response);
        assertEquals(ID, response.getId());
        assertEquals(MES, response.getMes());
        assertEquals(DESCRICAO_DESPESA, response.getDescricao());
        assertEquals(VALOR, response.getValor());
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
                .hasMessage(ERRO_NOT_FOUND)
                .isInstanceOf(CustomEntityNotFoundException.class);

        verify(repository).findById(anyLong());
        verify(repository, never()).save(any(Despesa.class));
    }

    @Test
    void whenDeleteThenSuccess() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(despesa));

        service.delete(ID);

        verify(repository).findById(anyLong());
        verify(repository).deleteById(anyLong());
    }

    @Test
    void whenDeleteThenThrowsNotFound() {

        assertThatThrownBy(() -> service.delete(ID))
                .hasMessage(ERRO_NOT_FOUND)
                .isInstanceOf(CustomEntityNotFoundException.class);

        verify(repository).findById(anyLong());
        verify(repository, never()).deleteById(anyLong());
    }

    private void createInstances() {

        TipoPagamento tipoPagamento = new TipoPagamento();
        tipoPagamento.setId(ID);

        CategoriaDespesa categoriaDespesa = new CategoriaDespesa();
        categoriaDespesa.setId(ID);

        TipoDespesa tipoDespesa = new TipoDespesa();
        tipoDespesa.setId(ID);
        tipoDespesa.setCategoriaDespesa(categoriaDespesa);

        despesa = new Despesa();
        despesa.setId(ID);
        despesa.setMes(MES);
        despesa.setValor(VALOR);
        despesa.setDescricao(DESCRICAO_DESPESA);
        despesa.setTipoPagamento(tipoPagamento);
        despesa.setTipoDespesa(tipoDespesa);

    }
}