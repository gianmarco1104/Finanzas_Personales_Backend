package finances_practice.gmejia.repository;

import finances_practice.gmejia.entity.CountriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountriesRepository extends JpaRepository<CountriesEntity, Integer> {
}
