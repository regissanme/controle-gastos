package br.com.rsanme.controlegastos.services.impl;

import br.com.rsanme.controlegastos.models.TipoDespesa;
import br.com.rsanme.controlegastos.repositories.TipoDespesaRepository;
import br.com.rsanme.controlegastos.services.ITipoDespesaService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 10/11/2023
 * Hora: 10:07
 */
public class TipoDespesaServiceImpl implements ITipoDespesaService {

    private final TipoDespesaRepository repository;

    public TipoDespesaServiceImpl(TipoDespesaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List findAll() {
        return repository.findAll();
    }

    @Override
    public Object findById(Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                String.format("Tipo de Despesa com Id %s não encontrado!", id))
                );
    }

    @Override
    public Object create(Object o) {
        return null;
    }

    @Override
    public Object update(Object o) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    private void findByDescricao(TipoDespesa tipoDespesa){

        Optional<TipoDespesa> byDescricao = repository.findByDescricao(tipoDespesa.getDescricao());

        if(byDescricao.isPresent() && !byDescricao.get().getId().equals(tipoDespesa.getId())){
            throw new EntityExistsException(
                    String.format(
                            "Um Tipo de Despesa com a descrição %s já existe!", tipoDespesa.getDescricao()
                    )
            );
        }

    }
}
