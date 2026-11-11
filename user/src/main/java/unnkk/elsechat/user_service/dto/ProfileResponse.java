package unnkk.elsechat.user_service.dto;

import jakarta.validation.constraints.NotBlank;

public record ProfileResponse(@NotBlank Long id, @NotBlank String username, @NotBlank String displayName,
                              String bio, String avatarUrl) {
}
