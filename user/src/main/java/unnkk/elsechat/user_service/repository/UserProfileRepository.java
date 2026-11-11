package unnkk.elsechat.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unnkk.elsechat.user_service.entity.UserProfile;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    public Optional<UserProfile> findByUsername(String username);
    public boolean existsByUsername(String username);
}
