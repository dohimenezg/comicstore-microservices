package com.dohimenezg.auth.dto.response;
import com.dohimenezg.auth.model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleResponseDTO {

    private Long id;
    private String name;
    private String description;

    public static RoleResponseDTO fromEntity(Role role) {
        RoleResponseDTO dto = new RoleResponseDTO();
        dto.setId(role.getId());
        dto.setName(role.getName());
        dto.setDescription(role.getDescription());
        return dto;
    }

}