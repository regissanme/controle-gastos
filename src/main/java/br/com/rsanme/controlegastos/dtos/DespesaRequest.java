package br.com.rsanme.controlegastos.dtos;

import br.com.rsanme.controlegastos.models.Despesa;
import br.com.rsanme.controlegastos.models.TipoDespesa;
import br.com.rsanme.controlegastos.models.TipoPagamento;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 13/12/2023
 * Hora: 15:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DespesaRequest {

    private Long id;

    @NotBlank(message = "O mês é obrigatório!")
    private String mes;

    @NotNull(message = "O valor precisa ser maior que 0 (zero)!")
    @DecimalMin(value = "0.01", message = "O valor precisa ser maior que 0 (zero)!")
    private BigDecimal valor;

    private String descricao;

    @NotNull
    private Long tipoPagamentoId;

    @NotNull
    private Long tipoDespesaId;

    public Despesa toModel() {
        TipoDespesa tipoDespesa = new TipoDespesa();
        tipoDespesa.setId(tipoDespesaId);

        TipoPagamento tipoPagamento = new TipoPagamento();
        tipoPagamento.setId(tipoPagamentoId);

        Despesa despesa = new Despesa();
        despesa.setId(id);
        despesa.setMes(mes);
        despesa.setValor(valor);
        despesa.setDescricao(descricao);
        despesa.setTipoDespesa(tipoDespesa);
        despesa.setTipoPagamento(tipoPagamento);

        return despesa;
    }
}
