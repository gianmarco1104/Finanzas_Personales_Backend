package finances_practice.gmejia.repository;

import finances_practice.gmejia.entity.GenderEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenderRepository extends JpaRepository<@NonNull GenderEntity,@NonNull Integer> {
}
