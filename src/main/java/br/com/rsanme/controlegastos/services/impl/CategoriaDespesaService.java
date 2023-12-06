package br.com.rsanme.controlegastos.services.impl;

import br.com.rsanme.controlegastos.exceptions.CustomEntityAlreadyExistsException;
import br.com.rsanme.controlegastos.exceptions.CustomEntityNotFoundException;
import br.com.rsanme.controlegastos.models.CategoriaDespesa;
import br.com.rsanme.controlegastos.repositories.CategoriaDespesaRepository;
import br.com.rsanme.controlegastos.services.ICrudService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 10/11/2023
 * Hora: 17:21
 */
@Service
public class CategoriaDespesaService implements ICrudService<CategoriaDespesa> {

    private final CategoriaDespesaRepository repository;

    public CategoriaDespesaService(CategoriaDespesaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CategoriaDespesa> findAll() {
        return repository.findAll();
    }

    @Override
    public CategoriaDespesa findById(Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new CustomEntityNotFoundException(
                                String.format("Categoria da Despesa com Id %s não encontrada!", id))
                );
    }

    @Override
    public CategoriaDespesa create(CategoriaDespesa categoriaDespesa) {
        findByDescricao(categoriaDespesa);

        return repository.save(categoriaDespesa);
    }

    @Override
    public CategoriaDespesa update(CategoriaDespesa categoriaDespesa) {
        findById(categoriaDespesa.getId());
        findByDescricao(categoriaDespesa);

        return repository.save(categoriaDespesa);
    }

    @Override
    public void delete(Long id) {
        findById(id);

        repository.deleteById(id);
    }

    private void findByDescricao(CategoriaDespesa categoriaDespesa) {

        Optional<CategoriaDespesa> byDescricao = repository.findByDescricao(categoriaDespesa.getDescricao());

        if (byDescricao.isPresent() && !byDescricao.get().getId().equals(categoriaDespesa.getId())) {
            throw new CustomEntityAlreadyExistsException(
                    String.format(
                            "Uma Categoria de Despesa com a descrição %s já existe!",
                            categoriaDespesa.getDescricao()
                    )
            );
        }
    }
}
