package br.com.rsanme.controlegastos.models;

import br.com.rsanme.controlegastos.auth.domain.models.UserApp;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 09/11/2023
 * Hora: 15:00
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A data é obrigatória!")
    @Column(nullable = false)
    private LocalDate data;

    @NotNull(message = "O valor precisa ser maior que 0 (zero)!")
    @DecimalMin(value = "0.01", message = "O valor precisa ser maior que 0 (zero)!")
    private BigDecimal valor;

    @NotNull(message = "A quantidade de parcelas é obrigatória!")
    @Min(value = 1, message = "A quantidade de parcelas deve ser maior ou igual a 1")
    private Integer parcelas;

    private Integer parcelaAtual;

    private String descricao;

    @NotNull
    @ManyToOne
    private TipoPagamento tipoPagamento;

    @NotNull
    @ManyToOne
    private TipoDespesa tipoDespesa;

    @NotNull
    @ManyToOne
    private UserApp user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Despesa despesa = (Despesa) o;
        return Objects.equals(id, despesa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Despesa{" +
                "id=" + id +
                ", data='" + data + '\'' +
                ", valor=" + valor +
                ", tipoPagamento=" + tipoPagamento +
                ", tipoDespesa=" + tipoDespesa +
                ", user=" + user.getId() +
                '}';
    }
}
