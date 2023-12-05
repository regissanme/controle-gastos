package br.com.rsanme.controlegastos.services.impl;

import br.com.rsanme.controlegastos.exceptions.CustomEntityAlreadyExistsException;
import br.com.rsanme.controlegastos.exceptions.CustomEntityNotFoundException;
import br.com.rsanme.controlegastos.models.CategoriaDespesa;
import br.com.rsanme.controlegastos.models.TipoDespesa;
import br.com.rsanme.controlegastos.repositories.CategoriaDespesaRepository;
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
 * Hora: 17:42
 */
@ExtendWith(MockitoExtension.class)
class CategoriaDespesaServiceImplTest {

    public static final Long ID = 1L;
    public static final String CATEGORIA_DESC_TRANSPORTE = "Transporte";
    public static final String TIPO_DESC_COMBUSTIVEL = "Combustível";
    public static final String ERRO_NOT_FOUND = "Categoria da Despesa com Id " + ID + " não encontrada!";
    public static final String ERRO_ALREADY_EXISTS =
            "Uma Categoria de Despesa com a descrição " + CATEGORIA_DESC_TRANSPORTE + " já existe!";
    public static final String ERRO_INVALID_DATA = "Dados inválidos!";

    @InjectMocks
    private CategoriaDespesaServiceImpl service;

    @Mock
    private CategoriaDespesaRepository repository;

    private CategoriaDespesa categoriaDespesa;

    private TipoDespesa tipoDespesa;

    @BeforeEach
    void setUp() {
        createInstances();
    }

    @Test
    void whenFindAllReturnList() {

        when(repository.findAll()).thenReturn(List.of(categoriaDespesa));

        List<CategoriaDespesa> listResponse = service.findAll();

        assertNotNull(listResponse);
        assertEquals(1, listResponse.size());
        assertEquals(CategoriaDespesa.class, listResponse.get(0).getClass());
        assertEquals(TipoDespesa.class, listResponse.get(0).getTiposDespesas().get(0).getClass());
        assertEquals(CATEGORIA_DESC_TRANSPORTE, listResponse.get(0).getDescricao());
        assertEquals(TIPO_DESC_COMBUSTIVEL, listResponse.get(0).getTiposDespesas().get(0).getDescricao());
        assertNotNull(listResponse.get(0).getTiposDespesas());

        verify(repository)
                .findAll();
    }

    @Test
    void whenFindByIdThrowsNotFound() {

        assertThatThrownBy(() -> service.findById(ID))
                .hasMessage(ERRO_NOT_FOUND)
                .isInstanceOf(CustomEntityNotFoundException.class);

        verify(repository)
                .findById(anyLong());
    }

    @Test
    void whenFindByIdThenReturnInstance() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(categoriaDespesa));

        CategoriaDespesa response = service.findById(ID);

        assertNotNull(response);
        assertEquals(ID, response.getId());
        assertEquals(CATEGORIA_DESC_TRANSPORTE, response.getDescricao());
        assertEquals(TIPO_DESC_COMBUSTIVEL, response.getTiposDespesas().get(0).getDescricao());

        verify(repository)
                .findById(anyLong());
    }

    @Test
    void whenCreateThenReturnSuccess() {
        CategoriaDespesa toSave = new CategoriaDespesa(null, CATEGORIA_DESC_TRANSPORTE, null);

        when(repository.save(toSave)).thenReturn(categoriaDespesa);

        CategoriaDespesa response = service.create(toSave);

        assertNotNull(response);
        assertEquals(ID, response.getId());
        assertEquals(CATEGORIA_DESC_TRANSPORTE, response.getDescricao());
        assertEquals(TIPO_DESC_COMBUSTIVEL, response.getTiposDespesas().get(0).getDescricao());

        assertEquals(categoriaDespesa.hashCode(), response.hashCode());
        assertTrue(categoriaDespesa.equals(response));
        assertFalse(categoriaDespesa.equals(null));
        assertFalse(categoriaDespesa.equals(toSave));
        assertTrue(categoriaDespesa.toString().contains("CategoriaDespesa"));

        verify(repository)
                .save(any());
    }

    @Test
    void whenCreateThenThrowsAlreadyExists() {
        CategoriaDespesa toSave = new CategoriaDespesa(null, CATEGORIA_DESC_TRANSPORTE, null);

        when(repository.findByDescricao(anyString())).thenReturn(Optional.of(categoriaDespesa));

        assertThatThrownBy(() -> service.create(toSave))
                .hasMessage(ERRO_ALREADY_EXISTS)
                .isInstanceOf(CustomEntityAlreadyExistsException.class);

        verify(repository)
                .findByDescricao(anyString());

        verify(repository, never())
                .save(any());
    }

    @Test
    void whenUpdateThenReturnSuccess() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(categoriaDespesa));
        when(repository.findByDescricao(anyString())).thenReturn(Optional.of(categoriaDespesa));
        when(repository.save(any())).thenReturn(categoriaDespesa);

        CategoriaDespesa response = service.update(categoriaDespesa);

        assertNotNull(response);
        assertEquals(ID, response.getId());
        assertEquals(CATEGORIA_DESC_TRANSPORTE, response.getDescricao());
        assertEquals(TIPO_DESC_COMBUSTIVEL, response.getTiposDespesas().get(0).getDescricao());

        verify(repository)
                .save(any());
    }

    @Test
    void whenUpdateThenThrowsNotFound() {

        assertThatThrownBy(() -> service.update(categoriaDespesa))
                .hasMessage(ERRO_NOT_FOUND)
                .isInstanceOf(CustomEntityNotFoundException.class);

        verify(repository)
                .findById(anyLong());

        verify(repository, never())
                .save(any());
    }

    @Test
    void whenUpdateThenThrowsAlreadyExists() {
        CategoriaDespesa toUpdate = new CategoriaDespesa(2L, CATEGORIA_DESC_TRANSPORTE, null);

        when(repository.findById(anyLong())).thenReturn(Optional.of(categoriaDespesa));
        when(repository.findByDescricao(anyString())).thenReturn(Optional.of(categoriaDespesa));

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

        when(repository.findById(anyLong())).thenReturn(Optional.of(categoriaDespesa));

        service.delete(ID);

        verify(repository)
                .findById(anyLong());

        verify(repository)
                .deleteById(anyLong());
    }

    @Test
    void whenDeleteThenThrowsNotFound() {

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
        categoriaDespesa.setId(ID);
        categoriaDespesa.setDescricao(CATEGORIA_DESC_TRANSPORTE);

        tipoDespesa = new TipoDespesa(ID, TIPO_DESC_COMBUSTIVEL, categoriaDespesa);

        categoriaDespesa.setTiposDespesas(List.of(tipoDespesa));
    }
}