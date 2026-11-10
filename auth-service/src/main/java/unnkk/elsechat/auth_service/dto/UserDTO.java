package unnkk.elsechat.auth_service.dto;

import jakarta.validation.constraints.NotBlank;

public record UserDTO(@NotBlank String username, @NotBlank String password) {
}
