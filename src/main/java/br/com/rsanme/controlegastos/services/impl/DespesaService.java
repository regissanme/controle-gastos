package br.com.rsanme.controlegastos.services.impl;

import br.com.rsanme.controlegastos.exceptions.BusinessException;
import br.com.rsanme.controlegastos.exceptions.CustomEntityNotFoundException;
import br.com.rsanme.controlegastos.models.Despesa;
import br.com.rsanme.controlegastos.repositories.DespesaRepository;
import br.com.rsanme.controlegastos.services.ICrudService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 04/12/2023
 * Hora: 22:43
 */
@Service
public class DespesaService implements ICrudService<Despesa> {

    private final DespesaRepository repository;

    public DespesaService(DespesaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Despesa> findAllByUser(Long userId) {

        if (userId == null || userId == 0) {
            throw new BusinessException("É necessário informar um usuário para buscar as despesas!");
        }
        return repository.findAllByUserId(userId);
    }

    public List<Despesa> findAllByUserAndYear(Long userId, Integer year){
        LocalDate start = LocalDate.of(year, 1, 1);
        LocalDate end = LocalDate.of(year, 12, 31);
        return repository.findAllByUserIdAndDataBetween(userId, start, end);
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
        if (despesa.getParcelas() > 1) {
            List<Despesa> despesas = new ArrayList<>();
            Despesa novaDespesa = null;
            for (int i = 0; i < despesa.getParcelas(); i++) {
                novaDespesa = new Despesa();
                novaDespesa.setId(despesa.getId());
                novaDespesa.setParcelas(despesa.getParcelas());
                novaDespesa.setValor(despesa.getValor());
                novaDespesa.setDescricao(despesa.getDescricao());
                novaDespesa.setTipoDespesa(despesa.getTipoDespesa());
                novaDespesa.setTipoPagamento(despesa.getTipoPagamento());
                novaDespesa.setUser(despesa.getUser());
                novaDespesa.setParcelaAtual(i+1);
                novaDespesa.setData(despesa.getData().plusMonths(Integer.toUnsignedLong(i)));
                despesas.add(novaDespesa);
            }
            List<Despesa> savedAll = repository.saveAll(despesas);
            return savedAll.get(0);
        }
        despesa.setParcelaAtual(1);
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
