package unnkk.elsechat.user_service.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateProfileRequest(String displayName, String bio, String avatarUrl) {
}
