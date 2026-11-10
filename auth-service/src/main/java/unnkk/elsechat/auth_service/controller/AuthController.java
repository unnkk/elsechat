package unnkk.elsechat.auth_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import unnkk.elsechat.auth_service.dto.UserDTO;
import unnkk.elsechat.auth_service.service.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@Valid  @RequestBody UserDTO body){
        String token;
        try {
            token = authService.register(body);
        } catch (UsernameAlreadyTakenException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", e.getMessage()));
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of(
                        "token", token
                ));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid  @RequestBody UserDTO body){
        String token;
        try {
            token = authService.login(body);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of(
                        "token", token
                ));
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, String>> getCurrentUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("username", username));
    }
}
