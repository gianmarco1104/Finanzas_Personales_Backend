package finances_practice.gmejia.repository;

import finances_practice.gmejia.dto.request.TransactionPerUserRequest;
import finances_practice.gmejia.dto.response.TransactionSummary;
import finances_practice.gmejia.entity.TransactionEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<@NonNull TransactionEntity,@NonNull Long> {
    @Query(value = "SELECT finance_personal.fn_get_dashboard_data(:userId)::text", nativeQuery = true)
    String getDashboardData(@Param("userId") Long userId);

    /*
    Se usa SpEL (Spring Expression Language)
    : -> Spring entiende que es un parametro que se debe inyectar
    #{} -> El contenido se evalua por Java antes de ser enviado a la BD
    #filter -> Referencia al nombre del @Param("filter")
    .month -> Spring llama internamente al metodo getMonth() igual con los otros se repite el proceso
     */
    @Query(value = "SELECT * FROM finance_personal.fn_get_transactions(" +
            ":userId, " + ":#{#filter.month}, " + ":#{#filter.year}, " + ":#{#filter.categoryId})", nativeQuery = true)
    List<TransactionSummary> findHistory(
            @Param("userId") Long userId,
            @Param("filter") TransactionPerUserRequest request);

    Optional<TransactionEntity> findByIdAndStatus(Long id, Boolean status);
}
