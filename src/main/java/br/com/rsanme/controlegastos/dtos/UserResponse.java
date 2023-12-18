package br.com.rsanme.controlegastos.dtos;

import br.com.rsanme.controlegastos.models.UserApp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 16/12/2023
 * Hora: 16:41
 */
@Data
public class UserResponse {

    private Long id;
    private String username;
    private String name;
    private String cpf;
    private String role;
    private Boolean active;
    private Date birthDate;
    private LocalDateTime lastAccessAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;

    public static UserResponse toResponse(UserApp userApp, String token) {

        UserResponse response = new UserResponse();
        response.setId(userApp.getId());
        response.setUsername(userApp.getUsername());
        response.setName(userApp.getName());
        response.setCpf(userApp.getCpf());
        response.setRole(userApp.getRole());
        response.setActive(userApp.getActive());
        response.setBirthDate(userApp.getBirthDate());
        response.setLastAccessAt(userApp.getLastAccessAt());
        response.setToken(token);

        return response;
    }

    public static List<UserResponse> toListResponse(List<UserApp> allUsers) {
        return allUsers
                .stream()
                .map(
                        userApp -> toResponse(userApp, null)
                ).toList();
    }
}
