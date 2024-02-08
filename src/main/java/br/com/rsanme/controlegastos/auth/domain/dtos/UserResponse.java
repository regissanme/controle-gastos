package br.com.rsanme.controlegastos.auth.domain.dtos;

import br.com.rsanme.controlegastos.auth.domain.models.UserApp;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;
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
    private String role;
    private Boolean active;
    private LocalDate birthDate;
    private LocalDateTime lastAccessAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date expiration;

    public static UserResponse toResponse(UserApp userApp, String token, Date expiration) {

        UserResponse response = new UserResponse();
        response.setId(userApp.getId());
        response.setUsername(userApp.getUsername());
        response.setName(userApp.getName());
        response.setRole(userApp.getRole().replace("ROLE_", ""));
        response.setActive(userApp.getActive());
        response.setBirthDate(userApp.getBirthDate());
        response.setLastAccessAt(userApp.getLastAccessAt());
        response.setToken(token);
        response.setExpiration(expiration);

        return response;
    }

    public static List<UserResponse> toListResponse(List<UserApp> allUsers) {
        return allUsers
                .stream()
                .map(
                        userApp -> toResponse(userApp, null, null)
                ).toList();
    }
}
