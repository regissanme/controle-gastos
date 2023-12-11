package br.com.rsanme.controlegastos.services.impl;

import br.com.rsanme.controlegastos.exceptions.BusinessException;
import br.com.rsanme.controlegastos.exceptions.CustomEntityAlreadyExistsException;
import br.com.rsanme.controlegastos.exceptions.CustomEntityNotFoundException;
import br.com.rsanme.controlegastos.models.TipoDespesa;
import br.com.rsanme.controlegastos.repositories.TipoDespesaRepository;
import br.com.rsanme.controlegastos.utils.TipoDespesaMock;
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
class TipoDespesaServiceTest {


    @InjectMocks
    private TipoDespesaService service;

    @Mock
    private TipoDespesaRepository repository;

    private TipoDespesa tipoDespesa;

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
        assertEquals(TipoDespesaMock.DESCRICAO_COMBUSTIVEL, listResponse.get(0).getDescricao());
        assertNotNull(listResponse.get(0).getCategoriaDespesa().getDescricao());
        assertNotNull(listResponse.get(0).getCategoriaDespesa());

        verify(repository, times(1))
                .findAll();
    }

    @Test
    void whenFindByIdThenReturnInstance() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(tipoDespesa));

        TipoDespesa response = service.findById(TipoDespesaMock.ID);

        assertNotNull(response);
        assertEquals(TipoDespesaMock.ID, response.getId());
        assertEquals(TipoDespesaMock.DESCRICAO_COMBUSTIVEL, response.getDescricao());
        assertNotNull(response.getCategoriaDespesa().getDescricao());

        verify(repository).findById(anyLong());
    }

    @Test
    void whenFindByIdThenThrowsNotFound() {

        assertThatThrownBy(() -> service.findById(TipoDespesaMock.ID))
                .hasMessage(TipoDespesaMock.ERROR_MESSAGE_NOT_FOUND)
                .isInstanceOf(CustomEntityNotFoundException.class);

        verify(repository).findById(anyLong());
    }

    @Test
    void whenCreateThenReturnSuccess() {

        TipoDespesa toSave = TipoDespesaMock.getTipoDespesaToSave();

        when(repository.save(toSave)).thenReturn(tipoDespesa);

        TipoDespesa response = service.create(toSave);

        assertNotNull(response);
        assertEquals(TipoDespesaMock.ID, response.getId());
        assertEquals(TipoDespesaMock.DESCRICAO_COMBUSTIVEL, response.getDescricao());
        assertNotNull(response.getCategoriaDespesa().getDescricao());
        assertEquals(tipoDespesa.hashCode(), response.hashCode());
        assertTrue(tipoDespesa.equals(response));
        assertFalse(tipoDespesa.equals(null));
        assertFalse(tipoDespesa.equals(toSave));
        assertTrue(tipoDespesa.toString().contains("TipoDespesa"));

        verify(repository).save(any());
    }

    @Test
    void whenCreateThenTrowsAlreadyExists() {

        TipoDespesa toSave = TipoDespesaMock.getTipoDespesaToSave();

        when(repository.findByDescricao(anyString())).thenReturn(Optional.of(tipoDespesa));

        assertThatThrownBy(() -> service.create(toSave))
                .hasMessage(TipoDespesaMock.ERROR_MESSAGE_ALREADY_EXISTS)
                .isInstanceOf(CustomEntityAlreadyExistsException.class);

        verify(repository).findByDescricao(anyString());

        verify(repository, never()).save(any());
    }

    @Test
    void whenCreateThenTrowsInvalidData() {
        TipoDespesa toSave = new TipoDespesa();

        assertThatThrownBy(() -> service.create(toSave))
                .hasMessage(TipoDespesaMock.ERRO_INVALID_DATA)
                .isInstanceOf(BusinessException.class);

        verify(repository, never()).findByDescricao(anyString());

        verify(repository, never()).save(any());
    }

    @Test
    void whenUpdateThenReturnSuccess() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(tipoDespesa));
        when(repository.findByDescricao(anyString())).thenReturn(Optional.of(tipoDespesa));
        when(repository.save(any())).thenReturn(tipoDespesa);

        TipoDespesa response = service.update(tipoDespesa);

        assertNotNull(response);
        assertEquals(TipoDespesaMock.ID, response.getId());
        assertEquals(TipoDespesaMock.DESCRICAO_COMBUSTIVEL, response.getDescricao());
        assertNotNull(response.getCategoriaDespesa());

        verify(repository).save(any());
    }

    @Test
    void whenUpdateThenTrowsNotFound() {

        assertThatThrownBy(() -> service.update(tipoDespesa))
                .hasMessage(TipoDespesaMock.ERROR_MESSAGE_NOT_FOUND)
                .isInstanceOf(CustomEntityNotFoundException.class);

        verify(repository).findById(anyLong());

        verify(repository, never()).save(any());
    }

    @Test
    void whenUpdateThenTrowsAlreadyExists() {
        TipoDespesa toUpdate = TipoDespesaMock.getTipoDespesaToUpdate();

        when(repository.findById(anyLong())).thenReturn(Optional.of(tipoDespesa));
        when(repository.findByDescricao(anyString())).thenReturn(Optional.of(tipoDespesa));

        assertThatThrownBy(() -> service.update(toUpdate))
                .hasMessage(TipoDespesaMock.ERROR_MESSAGE_ALREADY_EXISTS)
                .isInstanceOf(CustomEntityAlreadyExistsException.class);

        verify(repository).findByDescricao(anyString());

        verify(repository, never()).save(any());
    }

    @Test
    void whenDeleteThenSuccess() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(tipoDespesa));

        service.delete(TipoDespesaMock.ID);

        verify(repository).findById(anyLong());
    }

    @Test
    void whenDeleteThenTrowsNotFound() {

        assertThatThrownBy(() -> service.delete(TipoDespesaMock.ID))
                .hasMessage(TipoDespesaMock.ERROR_MESSAGE_NOT_FOUND)
                .isInstanceOf(CustomEntityNotFoundException.class);

        verify(repository).findById(anyLong());

        verify(repository, never()).deleteById(anyLong());
    }

    private void createInstances() {
        tipoDespesa = TipoDespesaMock.getTipoDespesa();
    }
}