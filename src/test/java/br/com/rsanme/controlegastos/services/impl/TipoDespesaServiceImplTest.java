package br.com.rsanme.controlegastos.services.impl;

import br.com.rsanme.controlegastos.models.CategoriaDespesa;
import br.com.rsanme.controlegastos.models.TipoDespesa;
import br.com.rsanme.controlegastos.repositories.TipoDespesaRepository;
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
import static org.mockito.Mockito.*;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 10/11/2023
 * Hora: 10:52
 */
@ExtendWith(MockitoExtension.class)
class TipoDespesaServiceImplTest {

    public static final Long ID = 1l;
    public static final String DESCRICAO_TRANSPORTE = "Transporte";
    public static final String DESCRICAO_COMBUSTIVEL = "Combustível";
    public static final String ERRO_NOT_FOUND = "Tipo de Despesa com Id " + ID + " não encontrado!";
    public static final String ERRO_ALREADY_EXISTS = "Um Tipo de Despesa com a descrição " + DESCRICAO_COMBUSTIVEL + " já existe!";


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
                .isInstanceOf(EntityNotFoundException.class);

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

        verify(repository, times(1))
                .save(any());
    }

    @Test
    void whenCreateThenTrowsAlreadyExists() {
        TipoDespesa toSave = new TipoDespesa(null, DESCRICAO_COMBUSTIVEL, categoriaDespesa);

        when(repository.findByDescricao(anyString())).thenReturn(Optional.of(tipoDespesa));

        assertThatThrownBy(() -> service.create(toSave))
                .hasMessage(ERRO_ALREADY_EXISTS)
                .isInstanceOf(EntityExistsException.class);

        verify(repository, times(1))
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

        verify(repository, times(1))
                .save(any());
    }

    @Test
    void whenUpdateThenTrowsAlreadyExists() {
        TipoDespesa toUpdate = new TipoDespesa(2L, DESCRICAO_COMBUSTIVEL, categoriaDespesa);

        when(repository.findById(anyLong())).thenReturn(Optional.of(tipoDespesa));
        when(repository.findByDescricao(anyString())).thenReturn(Optional.of(tipoDespesa));

        assertThatThrownBy(() -> service.update(toUpdate))
                .hasMessage(ERRO_ALREADY_EXISTS)
                .isInstanceOf(EntityExistsException.class);

        verify(repository, times(1))
                .findByDescricao(anyString());

        verify(repository, never())
                .save(any());
    }

    @Test
    void whenDeleteThenSuccess() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(tipoDespesa));

        service.delete(ID);

        verify(repository, times(1))
                .findById(anyLong());
    }

    private void createInstances() {
        categoriaDespesa = new CategoriaDespesa();
        categoriaDespesa.setId(1L);
        categoriaDespesa.setDescricao(DESCRICAO_TRANSPORTE);
        categoriaDespesa.setTipoDespesa(null);

        tipoDespesa = new TipoDespesa(ID, DESCRICAO_COMBUSTIVEL, categoriaDespesa);
    }
}