package br.com.rsanme.controlegastos.services;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 09/11/2023
 * Hora: 16:48
 */
@Service
public interface ICrudService<T> {

    default List<T> findAll() {
        return Collections.emptyList();
    }

    T findById(Long id);

    T create(T t);

    T update(T t);

    void delete(Long id);

    default List<T> findAllByUser(Long userId) {
        return Collections.emptyList();
    }
}
