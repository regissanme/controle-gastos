package br.com.rsanme.controlegastos.repositories;

import br.com.rsanme.controlegastos.models.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 09/11/2023
 * Hora: 16:35
 */
@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {
}
