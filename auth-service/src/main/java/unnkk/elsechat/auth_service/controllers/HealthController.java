package unnkk.elsechat.auth_service.controllers;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class HealthController {
    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }
}
