package br.com.rsanme.controlegastos.utils;

import br.com.rsanme.controlegastos.dtos.TipoDespesaRequest;
import br.com.rsanme.controlegastos.models.TipoDespesa;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 07/12/2023
 * Hora: 00:12
 */
public class TipoDespesaMock {

    public static final Long ID = 1L;
    public static final String DESCRICAO_COMBUSTIVEL = "Combustível";
    public static final String ERROR_MESSAGE_NOT_FOUND = "Tipo de Despesa com Id " + ID + " não encontrado!";
    public static final String ERROR_MESSAGE_ALREADY_EXISTS = "Um Tipo de Despesa com a descrição " + DESCRICAO_COMBUSTIVEL + " já existe!";
    public static final String ERRO_INVALID_DATA = "Dados inválidos!";

    public static TipoDespesa getTipoDespesa() {
        TipoDespesa tipoDespesa = new TipoDespesa();
        tipoDespesa.setId(ID);
        tipoDespesa.setDescricao(DESCRICAO_COMBUSTIVEL);
        tipoDespesa.setCategoriaDespesa(CategoriaDespesaMock.getCategoriaDespesa());

        return tipoDespesa;
    }

    public static TipoDespesa getTipoDespesaToSave() {
        TipoDespesa tipoDespesa = getTipoDespesa();
        tipoDespesa.setId(null);

        return tipoDespesa;
    }

    public static TipoDespesa getTipoDespesaToUpdate() {
        TipoDespesa tipoDespesa = getTipoDespesa();
        tipoDespesa.setId(2L);

        return tipoDespesa;
    }

    public static TipoDespesaRequest getTipoDespesaRequestToSave() {

        TipoDespesaRequest toSave = new TipoDespesaRequest();
        toSave.setId(null);
        toSave.setDescricao(DESCRICAO_COMBUSTIVEL);
        toSave.setCategoriaDespesaId(ID);

        System.out.println("toSave:" + toSave);

        return toSave;
    }

    public static TipoDespesaRequest getTipoDespesaRequestToUpdate() {
        TipoDespesaRequest tipoDespesa = getTipoDespesaRequestToSave();
        tipoDespesa.setId(2L);


        return tipoDespesa;
    }
}
