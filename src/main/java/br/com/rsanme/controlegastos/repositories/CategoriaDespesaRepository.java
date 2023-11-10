package br.com.rsanme.controlegastos.repositories;

import br.com.rsanme.controlegastos.models.CategoriaDespesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 09/11/2023
 * Hora: 16:36
 */
@Repository
public interface CategoriaDespesaRepository extends JpaRepository<CategoriaDespesa, Long> {
    Optional<CategoriaDespesa> findByDescricao(String descricao);
}
