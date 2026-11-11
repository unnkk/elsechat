package unnkk.elsechat.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unnkk.elsechat.user.dto.ProfileResponse;
import unnkk.elsechat.user.dto.UpdateProfileRequest;
import unnkk.elsechat.user.service.UserProfileService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/")
public class UserProfileController {
    private final UserProfileService userProfileService;

    @GetMapping("/{username}")
    public ResponseEntity<ProfileResponse> getUserProfile(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK).body(userProfileService.getProfile(username));
    }

    @PatchMapping("/me")
    public ResponseEntity<ProfileResponse> updateUserProfile(@Valid @RequestBody UpdateProfileRequest userProfile,
                                             @RequestHeader(name = "X-Username", required = true) String username) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userProfileService.updateProfile(username, userProfile));
    }
}
