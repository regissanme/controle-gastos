package br.com.rsanme.controlegastos.repositories;

import br.com.rsanme.controlegastos.models.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Projeto: controle-gastos
 * Desenvolvedor: Reginaldo Santos de Medeiros (regissanme)
 * Data: 09/11/2023
 * Hora: 16:35
 */
@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    List<Despesa> findAllByUserId(Long userId);

    List<Despesa> findAllByUserIdAndDataBetween(Long userId, LocalDate start, LocalDate end);

    @Query(
            value = "select * from controle_gastos.despesa d " +
                    "where d.user_id=:userId and year(d.data)=:year and month(d.data)=:month " +
                    "order by year(d.data)",
            nativeQuery = true
    )
    List<Despesa> findAllByUserIdAndYearAndMonth(Long userId, Integer year, Integer month);

    @Query(
            value = "select distinct year(d.data) from controle_gastos.despesa d where d.user_id=:userId order by year(d.data)",
            nativeQuery = true
    )
    List<Integer> listExpensesYearsByUser(Long userId);

    @Query(
            value = "select distinct month(d.data) from controle_gastos.despesa d " +
                    "where d.user_id=:userId and year(d.data)=:year order by month(d.data)",
            nativeQuery = true
    )
    List<Integer> listExpensesMonthsByUserAndYear(Long userId, Integer year);
}
