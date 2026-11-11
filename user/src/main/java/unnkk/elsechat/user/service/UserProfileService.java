package unnkk.elsechat.user.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import unnkk.elsechat.user.dto.ProfileResponse;
import unnkk.elsechat.user.dto.UpdateProfileRequest;
import unnkk.elsechat.user.entity.UserProfile;
import unnkk.elsechat.user.expection.UsernameAlreadyTakenException;
import unnkk.elsechat.user.expection.ProfileNotFoundException;
import unnkk.elsechat.user.repository.UserProfileRepository;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;

    public ProfileResponse createProfile(@NotNull String username, String displayName) {
        if(userProfileRepository.existsByUsername(username)){
            throw new UsernameAlreadyTakenException("Username is already taken");
        }
        UserProfile profile = new UserProfile();
        profile.setUsername(username);
        if(displayName != null){
            profile.setDisplayName(displayName);
        }else {
            profile.setDisplayName(username);
        }
        profile = userProfileRepository.save(profile);
        return new ProfileResponse(profile.getId(), profile.getUsername(), profile.getDisplayName(),
                profile.getBio(), profile.getAvatarUrl());
    }

    public ProfileResponse getProfile(@NotNull String username) {
        UserProfile userProfile = userProfileRepository.findByUsername(username)
                .orElseThrow(() -> new ProfileNotFoundException("Profile with specified username is absent"));
        return new ProfileResponse(userProfile.getId(), userProfile.getUsername(),
                userProfile.getDisplayName(), userProfile.getBio(), userProfile.getAvatarUrl());
    }

    public ProfileResponse updateProfile(@NotNull String username, @NotNull UpdateProfileRequest request) {
        UserProfile profile = userProfileRepository.findByUsername(username)
                .orElseThrow(() -> new ProfileNotFoundException("Profile with specified username is absent"));
        if(request.displayName() != null) {
            profile.setDisplayName(request.displayName());
        }
        if(request.bio() != null) {
            profile.setBio(request.bio());
        }
        if(request.avatarUrl() != null) {
            profile.setAvatarUrl(request.avatarUrl());
        }
        userProfileRepository.save(profile);

        return new ProfileResponse(profile.getId(), profile.getUsername(), profile.getDisplayName(),
                profile.getBio(), profile.getAvatarUrl());
    }
}
