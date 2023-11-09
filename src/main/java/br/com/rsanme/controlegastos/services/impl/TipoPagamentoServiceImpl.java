package br.com.rsanme.controlegastos.services.impl;

import br.com.rsanme.controlegastos.models.TipoPagamento;
import br.com.rsanme.controlegastos.repositories.TipoPagamentoRepository;
import br.com.rsanme.controlegastos.services.ITipoPagamentoService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 09/11/2023
 * Hora: 16:52
 */
public class TipoPagamentoServiceImpl implements ITipoPagamentoService<TipoPagamento> {

    private final TipoPagamentoRepository tipoPagamentoRepository;

    public TipoPagamentoServiceImpl(TipoPagamentoRepository tipoPagamentoRepository) {
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
                        () -> new EntityNotFoundException(
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

        return tipoPagamentoRepository.save(tipoPagamento);
    }

    @Override
    public void delete(Long id) {
        findById(id);
        tipoPagamentoRepository.deleteById(id);
    }

    private void findByTipo(TipoPagamento tipoPagamento) {
        Optional<TipoPagamento> byTipo = tipoPagamentoRepository.findByTipo(tipoPagamento.getTipo());

        if (byTipo.isPresent() && byTipo.get().getId().equals(tipoPagamento.getId())) {
            throw new EntityExistsException(
                    String.format("Um Tipo de Pagamento com o tipo %s já existe!", tipoPagamento.getTipo())
            );
        }
    }
}
