package br.com.rsanme.controlegastos.exceptions.handlers;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 04/12/2023
 * Hora: 23:22
 */

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomErrorResponse {

    private Integer status;
    private LocalDateTime timestamp;
    private String title;
    private List<FieldWithError> fields;

    @Getter
    @AllArgsConstructor
    public static class FieldWithError {
        private String fieldName;
        private String errorMessage;
    }
}
