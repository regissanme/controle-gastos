package br.com.rsanme.controlegastos.models;

import jakarta.persistence.*;
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

    @Column(nullable = false)
    private String mes;


    private BigDecimal valor;

    private String descricao;

    @OneToOne
    private TipoPagamento tipoPagamento;

    @OneToOne
    private TipoDespesa tipoDespesa;

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
                '}';
    }
}
