package finances_practice.gmejia.repository;

import finances_practice.gmejia.entity.UserEntity;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<@NonNull UserEntity, @NonNull Long> {
    //JPA cuenta con metodos ya listos con consultas establecidas
    //Optional -> Se usa para evitar el NullPointerException
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);

    //Admin
    List<UserEntity> findAllByEnabledAndRole_NameOrderByIdAsc(boolean enabled, String roleName);
}
