package br.com.rsanme.controlegastos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tipo_despesa")
public class TipoDespesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A descricao é obrigatória!")
    @Column(unique = true, nullable = false)
    private String descricao;

    @NotNull(message = "A categoria da despesa é obrigatória!")
    @ManyToOne
    @JoinColumn(name = "categoria_despesa_id")
    private CategoriaDespesa categoriaDespesa;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoDespesa that = (TipoDespesa) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TipoDespesa{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", categoriaDespesa=" + categoriaDespesa +
                '}';
    }
}