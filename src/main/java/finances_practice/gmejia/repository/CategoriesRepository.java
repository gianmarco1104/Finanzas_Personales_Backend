package finances_practice.gmejia.repository;

import finances_practice.gmejia.entity.CategoriesEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends JpaRepository<@NonNull CategoriesEntity,@NonNull Integer> {
}
