package br.com.rsanme.controlegastos.services.impl;

import br.com.rsanme.controlegastos.exceptions.BusinessException;
import br.com.rsanme.controlegastos.exceptions.CustomEntityAlreadyExistsException;
import br.com.rsanme.controlegastos.exceptions.CustomEntityNotFoundException;
import br.com.rsanme.controlegastos.models.CategoriaDespesa;
import br.com.rsanme.controlegastos.models.TipoDespesa;
import br.com.rsanme.controlegastos.repositories.TipoDespesaRepository;
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
import static org.mockito.Mockito.*;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 10/11/2023
 * Hora: 10:52
 */
@ExtendWith(MockitoExtension.class)
class TipoDespesaServiceImplTest {

    public static final Long ID = 1L;
    public static final String DESCRICAO_TRANSPORTE = "Transporte";
    public static final String DESCRICAO_COMBUSTIVEL = "Combustível";
    public static final String ERRO_NOT_FOUND = "Tipo de Despesa com Id " + ID + " não encontrado!";
    public static final String ERRO_ALREADY_EXISTS = "Um Tipo de Despesa com a descrição " + DESCRICAO_COMBUSTIVEL + " já existe!";
    public static final String ERRO_INVALID_DATA = "Dados inválidos!";


    @InjectMocks
    private TipoDespesaServiceImpl service;

    @Mock
    private TipoDespesaRepository repository;

    private TipoDespesa tipoDespesa;
    private CategoriaDespesa categoriaDespesa;

    @BeforeEach
    void setUp() {
        createInstances();
    }

    @Test
    void whenFindAllThenReturnList() {
        when(repository.findAll()).thenReturn(List.of(tipoDespesa));

        List<TipoDespesa> listResponse = service.findAll();

        assertNotNull(listResponse);
        assertEquals(1, listResponse.size());
        assertEquals(TipoDespesa.class, listResponse.get(0).getClass());
        assertEquals(DESCRICAO_COMBUSTIVEL, listResponse.get(0).getDescricao());
        assertEquals(DESCRICAO_TRANSPORTE, listResponse.get(0).getCategoriaDespesa().getDescricao());
        assertNotNull(listResponse.get(0).getCategoriaDespesa());

        verify(repository, times(1))
                .findAll();
    }

    @Test
    void whenFindByIdThenReturnInstance() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(tipoDespesa));

        TipoDespesa response = service.findById(ID);

        assertNotNull(response);
        assertEquals(ID, response.getId());
        assertEquals(DESCRICAO_COMBUSTIVEL, response.getDescricao());
        assertEquals(DESCRICAO_TRANSPORTE, response.getCategoriaDespesa().getDescricao());

        verify(repository, times(1))
                .findById(anyLong());
    }

    @Test
    void whenFindByIdThenThrowsNotFound() {

        assertThatThrownBy(() -> service.findById(ID))
                .hasMessage(ERRO_NOT_FOUND)
                .isInstanceOf(CustomEntityNotFoundException.class);

        verify(repository, times(1))
                .findById(anyLong());
    }

    @Test
    void whenCreateThenReturnSuccess() {
        TipoDespesa toSave = new TipoDespesa(null, DESCRICAO_COMBUSTIVEL, categoriaDespesa);

        when(repository.save(toSave)).thenReturn(tipoDespesa);

        TipoDespesa response = service.create(toSave);

        assertNotNull(response);
        assertEquals(ID, response.getId());
        assertEquals(DESCRICAO_COMBUSTIVEL, response.getDescricao());
        assertEquals(DESCRICAO_TRANSPORTE, response.getCategoriaDespesa().getDescricao());
        assertEquals(tipoDespesa.hashCode(), response.hashCode());
        assertTrue(tipoDespesa.equals(response));
        assertFalse(tipoDespesa.equals(null));
        assertFalse(tipoDespesa.equals(toSave));
        assertTrue(tipoDespesa.toString().contains("TipoDespesa"));

        verify(repository)
                .save(any());
    }

    @Test
    void whenCreateThenTrowsAlreadyExists() {
        TipoDespesa toSave = new TipoDespesa(null, DESCRICAO_COMBUSTIVEL, categoriaDespesa);

        when(repository.findByDescricao(anyString())).thenReturn(Optional.of(tipoDespesa));

        assertThatThrownBy(() -> service.create(toSave))
                .hasMessage(ERRO_ALREADY_EXISTS)
                .isInstanceOf(CustomEntityAlreadyExistsException.class);

        verify(repository)
                .findByDescricao(anyString());

        verify(repository, never())
                .save(any());
    }

    @Test
    void whenCreateThenTrowsInvalidData() {
        TipoDespesa toSave = new TipoDespesa(null, DESCRICAO_COMBUSTIVEL, null);

        assertThatThrownBy(() -> service.create(toSave))
                .hasMessage(ERRO_INVALID_DATA)
                .isInstanceOf(BusinessException.class);

        verify(repository, never())
                .findByDescricao(anyString());

        verify(repository, never())
                .save(any());
    }

    @Test
    void whenUpdateThenReturnSuccess() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(tipoDespesa));
        when(repository.findByDescricao(anyString())).thenReturn(Optional.of(tipoDespesa));
        when(repository.save(any())).thenReturn(tipoDespesa);

        TipoDespesa response = service.update(tipoDespesa);

        assertNotNull(response);
        assertEquals(ID, response.getId());
        assertEquals(DESCRICAO_COMBUSTIVEL, response.getDescricao());
        assertEquals(DESCRICAO_TRANSPORTE, response.getCategoriaDespesa().getDescricao());

        verify(repository)
                .save(any());
    }

    @Test
    void whenUpdateThenTrowsNotFound() {

        assertThatThrownBy(() -> service.update(tipoDespesa))
                .hasMessage(ERRO_NOT_FOUND)
                .isInstanceOf(CustomEntityNotFoundException.class);

        verify(repository)
                .findById(anyLong());

        verify(repository, never())
                .save(any());
    }

    @Test
    void whenUpdateThenTrowsAlreadyExists() {
        TipoDespesa toUpdate = new TipoDespesa(2L, DESCRICAO_COMBUSTIVEL, categoriaDespesa);

        when(repository.findById(anyLong())).thenReturn(Optional.of(tipoDespesa));
        when(repository.findByDescricao(anyString())).thenReturn(Optional.of(tipoDespesa));

        assertThatThrownBy(() -> service.update(toUpdate))
                .hasMessage(ERRO_ALREADY_EXISTS)
                .isInstanceOf(CustomEntityAlreadyExistsException.class);

        verify(repository)
                .findByDescricao(anyString());

        verify(repository, never())
                .save(any());
    }

    @Test
    void whenDeleteThenSuccess() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(tipoDespesa));

        service.delete(ID);

        verify(repository)
                .findById(anyLong());
    }

    @Test
    void whenDeleteThenTrowsNotFound() {

        assertThatThrownBy(() -> service.delete(ID))
                .hasMessage(ERRO_NOT_FOUND)
                .isInstanceOf(CustomEntityNotFoundException.class);

        verify(repository)
                .findById(anyLong());

        verify(repository, never())
                .deleteById(anyLong());
    }

    private void createInstances() {
        categoriaDespesa = new CategoriaDespesa();
        categoriaDespesa.setId(1L);
        categoriaDespesa.setDescricao(DESCRICAO_TRANSPORTE);
        categoriaDespesa.setTiposDespesas(null);

        tipoDespesa = new TipoDespesa();
        tipoDespesa.setId(ID);
        tipoDespesa.setDescricao(DESCRICAO_COMBUSTIVEL);
        tipoDespesa.setCategoriaDespesa(categoriaDespesa);
    }
}