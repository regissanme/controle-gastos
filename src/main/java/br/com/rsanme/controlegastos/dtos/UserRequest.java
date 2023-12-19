package br.com.rsanme.controlegastos.dtos;

import br.com.rsanme.controlegastos.models.UserApp;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.Date;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 16/12/2023
 * Hora: 16:41
 */
@Data
public class UserRequest {

    private Long id;

    @NotBlank(message = "O usuário é obrigatório!")
    private String username;

    @NotBlank(message = "A senha é obrigatória!")
    private String password;

    @NotBlank(message = "O nome é obrigatório!")
    private String name;

    @NotBlank(message = "O cpf é obrigatório!")
    private String cpf;

    private LocalDate birthDate;

    public UserApp toModel(){
        UserApp userApp = new UserApp();
        userApp.setId(id);
        userApp.setUsername(username);
        userApp.setPassword(password);
        userApp.setName(name);
        userApp.setCpf(cpf);
        userApp.setBirthDate(birthDate);

        return userApp;
    }
}
