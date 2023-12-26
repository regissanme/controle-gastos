package br.com.rsanme.controlegastos.utils;

import br.com.rsanme.controlegastos.models.CategoriaDespesa;
import br.com.rsanme.controlegastos.models.TipoDespesa;

import java.util.List;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 07/12/2023
 * Hora: 00:08
 */
public class CategoriaDespesaMock {

    public static final Long ID = 1L;
    public static final String CATEGORIA_DESC_TRANSPORTE = "Transporte";
    public static final String ERROR_MESSAGE_NOT_FOUND = "Categoria da Despesa com Id " + ID + " não encontrada!";
    public static final String ERROR_MESSAGE_ALREADY_EXISTS =
            "Uma Categoria de Despesa com a descrição " + CATEGORIA_DESC_TRANSPORTE + " já existe!";
    public static final String ERRO_INVALID_DATA = "Dados inválidos!";

    public static CategoriaDespesa getCategoriaDespesa() {
        TipoDespesa tipoDespesa = new TipoDespesa();
        tipoDespesa.setId(ID);

        CategoriaDespesa categoriaDespesa = new CategoriaDespesa();
        categoriaDespesa.setId(ID);
        categoriaDespesa.setDescricao(CATEGORIA_DESC_TRANSPORTE);

        categoriaDespesa.setTiposDespesas(List.of(tipoDespesa));

        return categoriaDespesa;
    }

    public static CategoriaDespesa getCategoriaDespesaToSave() {
        TipoDespesa tipoDespesa = new TipoDespesa();
        tipoDespesa.setId(ID);

        return new CategoriaDespesa(
                null,
                CATEGORIA_DESC_TRANSPORTE,
                List.of(tipoDespesa)
        );
    }

    public static CategoriaDespesa getCategoriaDespesaToUpdate() {
        CategoriaDespesa toUpdate = getCategoriaDespesa();
        toUpdate.setId(2L);

        return toUpdate;
    }
}
