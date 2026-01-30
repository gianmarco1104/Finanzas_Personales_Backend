package finances_practice.gmejia.repository;

import finances_practice.gmejia.entity.RolesEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<@NonNull RolesEntity,@NonNull Integer> {
}
