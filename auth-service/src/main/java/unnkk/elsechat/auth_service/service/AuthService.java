package unnkk.elsechat.auth_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unnkk.elsechat.auth_service.dto.UserDTO;
import unnkk.elsechat.auth_service.entity.User;
import unnkk.elsechat.auth_service.exception.InvalidCredentialsException;
import unnkk.elsechat.auth_service.exception.UserNotFoundException;
import unnkk.elsechat.auth_service.exception.UsernameAlreadyTakenException;
import unnkk.elsechat.auth_service.repository.UserRepository;
import unnkk.elsechat.auth_service.util.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public String register(UserDTO request) throws UsernameAlreadyTakenException{
        if(userRepository.existsByUsername(request.username()))
            throw new UsernameAlreadyTakenException("Username is already taken");

        User user = new User();
        String passwordEncoded = passwordEncoder.encode(request.password());
        user.setUsername(request.username());
        user.setPassword(passwordEncoded);

        userRepository.save(user);
        return jwtUtil.generateToken(user.getUsername());
    }

    public String login(UserDTO request){
        String username = request.username();
        String password = request.password();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new InvalidCredentialsException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getUsername());
    }
}
