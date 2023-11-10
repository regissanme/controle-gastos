package br.com.rsanme.controlegastos.services.impl;

import br.com.rsanme.controlegastos.models.CategoriaDespesa;
import br.com.rsanme.controlegastos.models.TipoDespesa;
import br.com.rsanme.controlegastos.repositories.CategoriaDespesaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    }

    @Test
    void whenFindAllReturnList() {
    }

    @Test
    void whenFindByIdThenReturnInstance() {
    }

    @Test
    void whenFindByIdThenThrowsNotFound() {
    }

    @Test
    void whenCreateThenReturnSuccess() {
    }

    @Test
    void whenCreateThenThrowsAlreadyExists() {
    }

    @Test
    void whenUpdateThenReturnSuccess() {
    }

    @Test
    void whenUpdateThenThrowsNotFound() {
    }

    @Test
    void whenUpdateThenThrowsAlreadyExists() {
    }

    @Test
    void whenDeleteThenSuccess() {
    }

    @Test
    void whenDeleteThenThrowsNotFound() {
    }
}