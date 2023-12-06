package br.com.rsanme.controlegastos.services.impl;

import br.com.rsanme.controlegastos.exceptions.BusinessException;
import br.com.rsanme.controlegastos.exceptions.CustomEntityAlreadyExistsException;
import br.com.rsanme.controlegastos.exceptions.CustomEntityNotFoundException;
import br.com.rsanme.controlegastos.models.TipoDespesa;
import br.com.rsanme.controlegastos.repositories.TipoDespesaRepository;
import br.com.rsanme.controlegastos.services.ICrudService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 10/11/2023
 * Hora: 10:07
 */
@Service
public class TipoDespesaService implements ICrudService<TipoDespesa> {

    private final TipoDespesaRepository repository;

    public TipoDespesaService(TipoDespesaRepository repository) {
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
                        () -> new CustomEntityNotFoundException(
                                String.format("Tipo de Despesa com Id %s não encontrado!", id))
                );
    }

    @Override
    public TipoDespesa create(TipoDespesa tipoDespesa) {
        if (tipoDespesa.getCategoriaDespesa() == null) {
            throw new BusinessException(
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
            throw new CustomEntityAlreadyExistsException(
                    String.format(
                            "Um Tipo de Despesa com a descrição %s já existe!", tipoDespesa.getDescricao()
                    )
            );
        }

    }
}
