package unnkk.elsechat.auth_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unnkk.elsechat.auth_service.dto.AuthRequest;
import unnkk.elsechat.auth_service.entity.User;
import unnkk.elsechat.auth_service.exception.UsernameAlreadyTakenException;
import unnkk.elsechat.auth_service.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public long register(AuthRequest request) throws UsernameAlreadyTakenException{
        if(userRepository.existsByUsername(request.username()))
            throw new UsernameAlreadyTakenException("Username is already taken");

        User user = new User();
        String passwordEncoded = passwordEncoder.encode(request.password());
        user.setUsername(request.username());
        user.setPassword(passwordEncoded);

        userRepository.save(user);
        return user.getId();
    }
}
