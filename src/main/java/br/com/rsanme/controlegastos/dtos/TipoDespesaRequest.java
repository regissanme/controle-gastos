package br.com.rsanme.controlegastos.dtos;

import br.com.rsanme.controlegastos.models.CategoriaDespesa;
import br.com.rsanme.controlegastos.models.TipoDespesa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 13/12/2023
 * Hora: 14:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoDespesaRequest {

    private Long id;

    @NotBlank(message = "A descricao é obrigatória!")
    private String descricao;

    @NotNull(message = "O id da categoria da despesa é obrigatório!")
    private Long categoriaDespesaId;

    public TipoDespesa toModel() {
        CategoriaDespesa categoriaDespesa = new CategoriaDespesa();
        categoriaDespesa.setId(categoriaDespesaId);

        TipoDespesa tipoDespesa = new TipoDespesa();
        tipoDespesa.setId(id);
        tipoDespesa.setDescricao(descricao);

        tipoDespesa.setCategoriaDespesa(categoriaDespesa);

        return tipoDespesa;
    }
}
