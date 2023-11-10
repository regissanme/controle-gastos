package br.com.rsanme.controlegastos.services;

import java.util.List;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 09/11/2023
 * Hora: 21:20
 */
public interface ICategoriaDespesaService<T> {

    List<T> findAll();

    T findById(Long id);

    T create(T t);

    T update(T t);

    void delete(Long id);
}
