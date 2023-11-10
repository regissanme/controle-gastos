package br.com.rsanme.controlegastos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 09/11/2023
 * Hora: 14:34
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDespesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A descrição da categoria é obrigatória!")
    @Column(unique = true, nullable = false)
    private String descricao;

    @OneToMany(mappedBy = "categoriaDespesa")
    private List<TipoDespesa> tiposDespesas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<TipoDespesa> getTiposDespesas() {
        return tiposDespesas;
    }

    public void setTiposDespesas(List<TipoDespesa> tiposDespesas) {
        this.tiposDespesas = tiposDespesas;
    }

    public void addTipoDespesa(TipoDespesa tipoDespesa) {
        if (tiposDespesas == null) {
            this.tiposDespesas = new ArrayList<>();
        }
        tiposDespesas.add(tipoDespesa);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoriaDespesa that = (CategoriaDespesa) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CategoriaDespesa{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", tiposDespesas=" + tiposDespesas +
                '}';
    }
}
