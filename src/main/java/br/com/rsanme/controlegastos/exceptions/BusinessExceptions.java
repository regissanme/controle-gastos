package br.com.rsanme.controlegastos.exceptions;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 04/12/2023
 * Hora: 23:08
 */
public class BusinessExceptions extends RuntimeException {
    public BusinessExceptions(String message) {
        super(message);
    }
}
