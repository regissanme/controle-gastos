package br.com.rsanme.controlegastos.auth.domain.dtos;

import br.com.rsanme.controlegastos.auth.domain.models.UserApp;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 16/12/2023
 * Hora: 16:41
 */
@Data
public class UserRequest {

    private Long id;

    @NotBlank(message = "O email é obrigatório!")
    @Email(message = "Insira um email válido!")
    private String username;

    @NotBlank(message = "A senha é obrigatória!")
    private String password;

    @NotBlank(message = "O nome é obrigatório!")
    private String name;

    private LocalDate birthDate;

    public UserApp toModel() {
        UserApp userApp = new UserApp();
        userApp.setId(id);
        userApp.setUsername(username);
        userApp.setPassword(password);
        userApp.setName(name);
        userApp.setBirthDate(birthDate);

        return userApp;
    }
}
