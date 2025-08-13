package com.dohimenezg.auth.dto.response;
import java.util.Set;
import java.util.stream.Collectors;
import com.dohimenezg.auth.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String bio;
    private Long profilePictureId;
    private boolean enabled;
    private Set<RoleResponseDTO> roles; 

    public static UserResponseDTO fromEntity(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setAddress(user.getAddress());
        dto.setBio(user.getBio());
        dto.setProfilePictureId(user.getProfilePictureId());
        dto.setEnabled(user.isEnabled());
        dto.setRoles(
            user.getRoles()
                .stream()
                .map(RoleResponseDTO::fromEntity)
                .collect(Collectors.toSet())
        );
        return dto;
    }

}
