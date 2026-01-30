package finances_practice.gmejia.repository;

import finances_practice.gmejia.entity.TransactionTypesEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTypesRepository extends JpaRepository<@NonNull TransactionTypesEntity,@NonNull Integer> {
}
