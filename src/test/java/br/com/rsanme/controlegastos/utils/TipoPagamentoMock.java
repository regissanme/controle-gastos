package br.com.rsanme.controlegastos.utils;

import br.com.rsanme.controlegastos.models.TipoPagamento;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 06/12/2023
 * Hora: 23:34
 */
public class TipoPagamentoMock {

    public static final Long ID = 1L;
    public static final String TIPO_PAGAMENTO_DINHEIRO = "DINHEIRO";
    public static final String ERROR_MESSAGE_NOT_FOUND = "Tipo de Pagamento com Id " + ID + " não encontrado!";
    public static final String ERROR_MESSAGE_ALREADY_EXISTS = "Um Tipo de Pagamento do tipo " + TIPO_PAGAMENTO_DINHEIRO + " já existe!";


    public static TipoPagamento getTipoPagamento() {
        return new TipoPagamento(1L, TIPO_PAGAMENTO_DINHEIRO);
    }

    public static TipoPagamento getTipoPagamentoToSave() {
        return new TipoPagamento(null, TIPO_PAGAMENTO_DINHEIRO);
    }

    public static TipoPagamento getTipoPagamentoToUpdate() {
        return new TipoPagamento(1L, TIPO_PAGAMENTO_DINHEIRO);
    }
}
