package finances_practice.gmejia.repository;

import finances_practice.gmejia.entity.PaymentMethodsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodsRepository extends JpaRepository<PaymentMethodsEntity, Integer> {
}
