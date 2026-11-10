package unnkk.elsechat.auth.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import unnkk.elsechat.auth.dto.UserDTO;
import unnkk.elsechat.auth.exception.UsernameAlreadyTakenException;
import unnkk.elsechat.auth.repository.UserRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Testcontainers
@Transactional
public class AuthServiceIntegrationTests {
    @Autowired
    AuthService authService;
    @Autowired
    UserRepository userRepository;

    @Container
    @ServiceConnection
    final static PostgreSQLContainer postgreSQLContainer =
            new PostgreSQLContainer(DockerImageName.parse("postgres:16-alpine")
                    .asCompatibleSubstituteFor("postgres"));

    @Test
    void shouldRegisterUserSuccessfully(){
        UserDTO user = new UserDTO("admin", "admin");

        String token = authService.register(user);

        assertThat(token).isNotNull();
        assertThat(userRepository.existsByUsername(user.username())).isTrue();
    }

    @Test
    void shouldThrowExceptionWhenUsernameAlreadyExists(){
        UserDTO user = new UserDTO("admin", "admin");
        authService.register(user);

        assertThatThrownBy(() -> authService.register(user))
                .isInstanceOf(UsernameAlreadyTakenException.class);
    }
}
