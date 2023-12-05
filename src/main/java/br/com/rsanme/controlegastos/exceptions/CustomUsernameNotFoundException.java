package br.com.rsanme.controlegastos.exceptions;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 04/12/2023
 * Hora: 23:15
 */
public class CustomUsernameNotFoundException extends BusinessException {
    public CustomUsernameNotFoundException(String message) {
        super(message);
    }
}
