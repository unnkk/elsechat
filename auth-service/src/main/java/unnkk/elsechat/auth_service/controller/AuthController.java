package unnkk.elsechat.auth_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unnkk.elsechat.auth_service.dto.AuthRequest;
import unnkk.elsechat.auth_service.exception.UsernameAlreadyTakenException;
import unnkk.elsechat.auth_service.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid  @RequestBody AuthRequest body){
        long id;
        try {
            id = authService.register(body);
        } catch (UsernameAlreadyTakenException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(String.valueOf(id));
    }
}
