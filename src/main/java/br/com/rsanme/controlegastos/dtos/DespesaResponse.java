package br.com.rsanme.controlegastos.dtos;

import br.com.rsanme.controlegastos.models.Despesa;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 26/12/2023
 * Hora: 21:36
 */
@Data
public class DespesaResponse {

    private Long id;

    private String mes;

    private BigDecimal valor;

    private String descricao;

    private Long tipoPagamentoId;

    private Long tipoDespesaId;

    private Long userId;

    public static DespesaResponse toResponse(Despesa despesa) {
        DespesaResponse response = new DespesaResponse();
        response.setId(despesa.getId());
        response.setMes(despesa.getMes());
        response.setDescricao(despesa.getDescricao());
        response.setValor(despesa.getValor());
        response.setTipoDespesaId(despesa.getTipoDespesa().getId());
        response.setTipoPagamentoId(despesa.getTipoPagamento().getId());
        response.setUserId(despesa.getUser().getId());

        return response;
    }

    public static List<DespesaResponse> toListResponse(List<Despesa> despesas){
        return despesas.
                stream()
                .map(DespesaResponse::toResponse)
                .toList();
    }
}
