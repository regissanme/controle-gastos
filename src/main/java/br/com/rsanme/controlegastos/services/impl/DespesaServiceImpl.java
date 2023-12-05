package br.com.rsanme.controlegastos.services.impl;

import br.com.rsanme.controlegastos.exceptions.CustomEntityNotFoundException;
import br.com.rsanme.controlegastos.models.Despesa;
import br.com.rsanme.controlegastos.repositories.DespesaRepository;
import br.com.rsanme.controlegastos.services.IDespesaService;

import java.util.List;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 04/12/2023
 * Hora: 22:43
 */
public class DespesaServiceImpl implements IDespesaService<Despesa> {

    private final DespesaRepository repository;

    public DespesaServiceImpl(DespesaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Despesa> findAll(Long id) {
        return repository.findAll();
    }

    @Override
    public Despesa findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new CustomEntityNotFoundException("Despesa não encontrada com Id: " + id)
                );
    }

    @Override
    public Despesa create(Despesa despesa) {
        return repository.save(despesa);
    }

    @Override
    public Despesa update(Despesa despesa) {
        findById(despesa.getId());
        return repository.save(despesa);
    }

    @Override
    public void delete(Long id) {
        findById(id);
        repository.deleteById(id);
    }
}
