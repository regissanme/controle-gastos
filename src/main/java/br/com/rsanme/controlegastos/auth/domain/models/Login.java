package br.com.rsanme.controlegastos.auth.domain.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 16/12/2023
 * Hora: 17:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Login {

    @NotBlank(message = "O usuário é obrigatório!")
    private String username;

    @NotBlank(message = "A senha é obrigatória!")
    private String password;
}
