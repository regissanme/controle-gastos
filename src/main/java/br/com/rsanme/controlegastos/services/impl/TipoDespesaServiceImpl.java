package br.com.rsanme.controlegastos.services.impl;

import br.com.rsanme.controlegastos.models.TipoDespesa;
import br.com.rsanme.controlegastos.repositories.TipoDespesaRepository;
import br.com.rsanme.controlegastos.services.ITipoDespesaService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintDefinitionException;

import java.util.List;
import java.util.Optional;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 10/11/2023
 * Hora: 10:07
 */
public class TipoDespesaServiceImpl implements ITipoDespesaService<TipoDespesa> {

    private final TipoDespesaRepository repository;

    public TipoDespesaServiceImpl(TipoDespesaRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<TipoDespesa> findAll() {
        return repository.findAll();
    }

    @Override
    public TipoDespesa findById(Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                String.format("Tipo de Despesa com Id %s não encontrado!", id))
                );
    }

    @Override
    public TipoDespesa create(TipoDespesa tipoDespesa) {
        if (tipoDespesa.getCategoriaDespesa() == null) {
            throw new ConstraintDefinitionException(
                    "Dados inválidos!"
            );
        }

        findByDescricao(tipoDespesa);

        return repository.save(tipoDespesa);
    }

    @Override
    public TipoDespesa update(TipoDespesa tipoDespesa) {
        findById(tipoDespesa.getId());

        findByDescricao(tipoDespesa);

        return repository.save(tipoDespesa);
    }

    @Override
    public void delete(Long id) {
        findById(id);
        repository.deleteById(id);
    }

    private void findByDescricao(TipoDespesa tipoDespesa) {

        Optional<TipoDespesa> byDescricao = repository.findByDescricao(tipoDespesa.getDescricao());

        if (byDescricao.isPresent() && !byDescricao.get().getId().equals(tipoDespesa.getId())) {
            throw new EntityExistsException(
                    String.format(
                            "Um Tipo de Despesa com a descrição %s já existe!", tipoDespesa.getDescricao()
                    )
            );
        }

    }
}
