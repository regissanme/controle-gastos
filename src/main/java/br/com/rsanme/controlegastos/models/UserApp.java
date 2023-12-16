package br.com.rsanme.controlegastos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 15/12/2023
 * Hora: 22:33
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class UserApp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome de usuário é obrigatório!")
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "A senha é obrigatória!")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "O nome de usuário é obrigatório!")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "O nome de usuário é obrigatório!")
    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String role;

    @Column(columnDefinition = "boolean default true")
    private Boolean active;

    private Date birthDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastAccessAt;
    private LocalDateTime currentAccessAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserApp userApp = (UserApp) o;
        return Objects.equals(id, userApp.id) && Objects.equals(cpf, userApp.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cpf);
    }
}
