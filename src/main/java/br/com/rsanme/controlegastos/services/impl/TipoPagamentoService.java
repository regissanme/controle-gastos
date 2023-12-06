package br.com.rsanme.controlegastos.services.impl;

import br.com.rsanme.controlegastos.exceptions.CustomEntityAlreadyExistsException;
import br.com.rsanme.controlegastos.exceptions.CustomEntityNotFoundException;
import br.com.rsanme.controlegastos.models.TipoPagamento;
import br.com.rsanme.controlegastos.repositories.TipoPagamentoRepository;
import br.com.rsanme.controlegastos.services.ICrudService;

import java.util.List;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 09/11/2023
 * Hora: 16:52
 */
public class TipoPagamentoService implements ICrudService<TipoPagamento> {

    private final TipoPagamentoRepository tipoPagamentoRepository;

    public TipoPagamentoService(TipoPagamentoRepository tipoPagamentoRepository) {
        this.tipoPagamentoRepository = tipoPagamentoRepository;
    }

    @Override
    public List<TipoPagamento> findAll() {
        return tipoPagamentoRepository.findAll();
    }

    @Override
    public TipoPagamento findById(Long id) {
        return tipoPagamentoRepository.findById(id)
                .orElseThrow(
                        () -> new CustomEntityNotFoundException(
                                String.format("Tipo de Pagamento com Id %s não encontrado!", id))
                );
    }

    @Override
    public TipoPagamento create(TipoPagamento tipoPagamento) {
        findByTipo(tipoPagamento);

        return tipoPagamentoRepository.save(tipoPagamento);
    }

    @Override
    public TipoPagamento update(TipoPagamento tipoPagamento) {
        findById(tipoPagamento.getId());
        findByTipo(tipoPagamento);

        return tipoPagamentoRepository.save(tipoPagamento);
    }

    @Override
    public void delete(Long id) {
        findById(id);
        tipoPagamentoRepository.deleteById(id);
    }

    private void findByTipo(TipoPagamento tipoPagamento) {
        var byTipo = tipoPagamentoRepository.findByTipo(tipoPagamento.getTipo());

        if (byTipo.isPresent() && !byTipo.get().getId().equals(tipoPagamento.getId())) {
            throw new CustomEntityAlreadyExistsException(
                    String.format("Um Tipo de Pagamento com o tipo %s já existe!", tipoPagamento.getTipo())
            );
        }
    }
}
