package br.com.rsanme.controlegastos.exceptions;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 04/12/2023
 * Hora: 23:13
 */
public class CustomEntityAlreadyExistsException extends BusinessException {
    public CustomEntityAlreadyExistsException(String message) {
        super(message);
    }
}
