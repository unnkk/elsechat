package unnkk.elsechat.auth_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import unnkk.elsechat.auth_service.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
