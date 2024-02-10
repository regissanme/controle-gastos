package br.com.rsanme.controlegastos.utils;

import br.com.rsanme.controlegastos.dtos.DespesaRequest;
import br.com.rsanme.controlegastos.models.Despesa;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 11/12/2023
 * Hora: 23:09
 */
public class DespesaMock {

    public static final Long ID = 1L;
    public static final LocalDate DATA = LocalDate.of(1978, 11, 14);
    public static final String DESCRICAO_DESPESA = "Gasto com combustível";
    public static final Integer PARCELAS = 1;
    public static final Integer PARCELA_ATUAL = 1;
    public static final BigDecimal VALOR = new BigDecimal("200.54");
    public static final String ERROR_MESSAGE_NOT_FOUND = "Despesa não encontrada com Id: " + ID;
        public static final String ERROR_MESSAGE_BUSINESS_EXCEPTION = "É necessário informar um usuário para buscar as despesas!";

    public static Despesa getDespesa() {
        Despesa despesa = new Despesa();
        despesa.setId(ID);
        despesa.setData(DATA);
        despesa.setValor(VALOR);
        despesa.setParcelas(PARCELAS);
        despesa.setParcelaAtual(PARCELA_ATUAL);
        despesa.setDescricao(DESCRICAO_DESPESA);
        despesa.setTipoPagamento(TipoPagamentoMock.getTipoPagamento());
        despesa.setTipoDespesa(TipoDespesaMock.getTipoDespesa());
        despesa.setUser(UserAppMock.getAdminUser());

        return despesa;
    }

    public static Despesa getDespesaToSave() {
        return new Despesa(null,
                DATA,
                VALOR,
                PARCELAS,
                PARCELA_ATUAL,
                DESCRICAO_DESPESA,
                TipoPagamentoMock.getTipoPagamento(),
                TipoDespesaMock.getTipoDespesa(),
                UserAppMock.getAdminUser()
        );
    }

    public static Despesa getDespesaToUpdate() {
        Despesa despesa = getDespesa();
        despesa.setId(2L);

        return despesa;
    }

    public static DespesaRequest getDespesaRequestToSave() {
        DespesaRequest toSave = new DespesaRequest();
        toSave.setData(DATA);
        toSave.setValor(VALOR);
        toSave.setParcelas(PARCELAS);
        toSave.setDescricao(DESCRICAO_DESPESA);
        toSave.setTipoDespesaId(ID);
        toSave.setTipoPagamentoId(ID);
        toSave.setUserId(ID);

        return toSave;
    }

    public static DespesaRequest getDespesaRequestToUpdate() {
        return new DespesaRequest(2L, DATA, VALOR, PARCELAS, DESCRICAO_DESPESA, ID, ID, ID);
    }
}
