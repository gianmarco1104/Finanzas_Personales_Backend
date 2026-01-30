package finances_practice.gmejia.repository;

import finances_practice.gmejia.entity.UserEntity;
import finances_practice.gmejia.entity.VerificationCodeEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationCodeRepository extends JpaRepository<@NonNull VerificationCodeEntity, @NonNull Long> {
    Optional<VerificationCodeEntity> findByUserAndCode(UserEntity user, String code);
    Optional<VerificationCodeEntity> findByUser(UserEntity user);
}
