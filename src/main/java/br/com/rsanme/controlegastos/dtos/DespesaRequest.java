package br.com.rsanme.controlegastos.dtos;

import br.com.rsanme.controlegastos.auth.domain.models.UserApp;
import br.com.rsanme.controlegastos.models.Despesa;
import br.com.rsanme.controlegastos.models.TipoDespesa;
import br.com.rsanme.controlegastos.models.TipoPagamento;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    @NotNull(message = "A Data é obrigatória!")
    private LocalDate data;

    @NotNull(message = "O valor precisa ser maior que 0 (zero)!")
    @DecimalMin(value = "0.01", message = "O valor precisa ser maior que 0 (zero)!")
    private BigDecimal valor;

    @NotNull(message = "A quantidade de parcelas é obrigatória!")
    @Min(value = 1, message = "A quantidade de parcelas deve ser maior ou igual a 1")
    private Integer parcelas;

    private String descricao;

    @NotNull
    private Long tipoPagamentoId;

    @NotNull
    private Long tipoDespesaId;

    @NotNull
    private Long userId;

    public Despesa toModel() {
        TipoDespesa tipoDespesa = new TipoDespesa();
        tipoDespesa.setId(tipoDespesaId);

        TipoPagamento tipoPagamento = new TipoPagamento();
        tipoPagamento.setId(tipoPagamentoId);

        UserApp userApp = new UserApp();
        userApp.setId(userId);

        Despesa despesa = new Despesa();
        despesa.setId(id);
        despesa.setData(data);
        despesa.setParcelas(parcelas);
        despesa.setValor(valor);
        despesa.setDescricao(descricao);
        despesa.setTipoDespesa(tipoDespesa);
        despesa.setTipoPagamento(tipoPagamento);
        despesa.setUser(userApp);

        return despesa;
    }
}
