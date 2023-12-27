package br.com.rsanme.controlegastos.models;

import br.com.rsanme.controlegastos.auth.domain.models.UserApp;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
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

    @NotBlank(message = "O mês é obrigatório!")
    @Column(nullable = false)
    private String mes;

    @NotNull(message = "O valor precisa ser maior que 0 (zero)!")
    @DecimalMin(value = "0.01", message = "O valor precisa ser maior que 0 (zero)!")
    private BigDecimal valor;

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
                ", mes='" + mes + '\'' +
                ", valor=" + valor +
                ", tipoPagamento=" + tipoPagamento +
                ", tipoDespesa=" + tipoDespesa +
                ", user=" + user.getId() +
                '}';
    }
}
