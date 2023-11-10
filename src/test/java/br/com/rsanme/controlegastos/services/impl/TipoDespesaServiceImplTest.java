package br.com.rsanme.controlegastos.services.impl;

import br.com.rsanme.controlegastos.models.CategoriaDespesa;
import br.com.rsanme.controlegastos.models.TipoDespesa;
import br.com.rsanme.controlegastos.repositories.TipoDespesaRepository;
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
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    private void createInstances(){
        categoriaDespesa = new CategoriaDespesa();
        categoriaDespesa.setId(1L);
        categoriaDespesa.setDescricao(DESCRICAO_TRANSPORTE);
        categoriaDespesa.setTipoDespesa(null);

        tipoDespesa = new TipoDespesa(ID, DESCRICAO_COMBUSTIVEL, categoriaDespesa);
    }
}