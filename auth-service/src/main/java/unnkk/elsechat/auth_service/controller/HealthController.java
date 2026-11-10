package unnkk.elsechat.auth_service.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class HealthController {
    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }
}
