package br.com.rsanme.controlegastos.services.impl;

import br.com.rsanme.controlegastos.exceptions.CustomEntityAlreadyExistsException;
import br.com.rsanme.controlegastos.exceptions.CustomEntityNotFoundException;
import br.com.rsanme.controlegastos.models.CategoriaDespesa;
import br.com.rsanme.controlegastos.models.TipoDespesa;
import br.com.rsanme.controlegastos.repositories.CategoriaDespesaRepository;
import br.com.rsanme.controlegastos.utils.CategoriaDespesaMock;
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
class CategoriaDespesaServiceTest {


    @InjectMocks
    private CategoriaDespesaService service;

    @Mock
    private CategoriaDespesaRepository repository;

    private CategoriaDespesa categoriaDespesa;


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
        assertEquals(CategoriaDespesaMock.CATEGORIA_DESC_TRANSPORTE, listResponse.get(0).getDescricao());
        assertNotNull(listResponse.get(0).getTiposDespesas().get(0));
        assertNotNull(listResponse.get(0).getTiposDespesas());

        verify(repository)
                .findAll();
    }

    @Test
    void whenFindByIdThrowsNotFound() {

        assertThatThrownBy(() -> service.findById(CategoriaDespesaMock.ID))
                .hasMessage(CategoriaDespesaMock.ERROR_MESSAGE_NOT_FOUND)
                .isInstanceOf(CustomEntityNotFoundException.class);

        verify(repository)
                .findById(anyLong());
    }

    @Test
    void whenFindByIdThenReturnInstance() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(categoriaDespesa));

        CategoriaDespesa response = service.findById(CategoriaDespesaMock.ID);

        assertNotNull(response);
        assertEquals(CategoriaDespesaMock.ID, response.getId());
        assertEquals(CategoriaDespesaMock.CATEGORIA_DESC_TRANSPORTE, response.getDescricao());
        assertNotNull(response.getTiposDespesas().get(0));

        verify(repository)
                .findById(anyLong());
    }

    @Test
    void whenCreateThenReturnSuccess() {
        CategoriaDespesa toSave = CategoriaDespesaMock.getCategoriaDespesaToSave();

        when(repository.save(toSave)).thenReturn(categoriaDespesa);

        CategoriaDespesa response = service.create(toSave);

        assertNotNull(response);
        assertEquals(CategoriaDespesaMock.ID, response.getId());
        assertEquals(CategoriaDespesaMock.CATEGORIA_DESC_TRANSPORTE, response.getDescricao());
        assertNotNull(response.getTiposDespesas().get(0));

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
        CategoriaDespesa toSave = CategoriaDespesaMock.getCategoriaDespesaToSave();

        when(repository.findByDescricao(anyString())).thenReturn(Optional.of(categoriaDespesa));

        assertThatThrownBy(() -> service.create(toSave))
                .hasMessage(CategoriaDespesaMock.ERROR_MESSAGE_ALREADY_EXISTS)
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
        assertEquals(CategoriaDespesaMock.ID, response.getId());
        assertEquals(CategoriaDespesaMock.CATEGORIA_DESC_TRANSPORTE, response.getDescricao());
        assertNotNull(response.getTiposDespesas().get(0));

        verify(repository)
                .save(any());
    }

    @Test
    void whenUpdateThenThrowsNotFound() {

        assertThatThrownBy(() -> service.update(categoriaDespesa))
                .hasMessage(CategoriaDespesaMock.ERROR_MESSAGE_NOT_FOUND)
                .isInstanceOf(CustomEntityNotFoundException.class);

        verify(repository)
                .findById(anyLong());

        verify(repository, never())
                .save(any());
    }

    @Test
    void whenUpdateThenThrowsAlreadyExists() {
        CategoriaDespesa toUpdate = CategoriaDespesaMock.getCategoriaDespesaToUpdate();

        when(repository.findById(anyLong())).thenReturn(Optional.of(categoriaDespesa));
        when(repository.findByDescricao(anyString())).thenReturn(Optional.of(categoriaDespesa));

        assertThatThrownBy(() -> service.update(toUpdate))
                .hasMessage(CategoriaDespesaMock.ERROR_MESSAGE_ALREADY_EXISTS)
                .isInstanceOf(CustomEntityAlreadyExistsException.class);

        verify(repository)
                .findByDescricao(anyString());

        verify(repository, never())
                .save(any());
    }

    @Test
    void whenDeleteThenSuccess() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(categoriaDespesa));

        service.delete(CategoriaDespesaMock.ID);

        verify(repository)
                .findById(anyLong());

        verify(repository)
                .deleteById(anyLong());
    }

    @Test
    void whenDeleteThenThrowsNotFound() {

        assertThatThrownBy(() -> service.delete(CategoriaDespesaMock.ID))
                .hasMessage(CategoriaDespesaMock.ERROR_MESSAGE_NOT_FOUND)
                .isInstanceOf(CustomEntityNotFoundException.class);

        verify(repository)
                .findById(anyLong());

        verify(repository, never())
                .deleteById(anyLong());
    }

    private void createInstances() {

        categoriaDespesa = CategoriaDespesaMock.getCategoriaDespesa();
    }
}