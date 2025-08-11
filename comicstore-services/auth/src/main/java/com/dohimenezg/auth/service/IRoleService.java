package com.dohimenezg.auth.service;
import java.util.List;
import com.dohimenezg.auth.dto.request.RoleRequestDTO;
import com.dohimenezg.auth.dto.response.RoleResponseDTO;

public interface IRoleService {

    List<RoleResponseDTO> getAllRoles();
    RoleResponseDTO getRoleById(Long id);
    RoleResponseDTO createRole(RoleRequestDTO requestDTO);
    RoleResponseDTO updateRole(Long id, RoleRequestDTO requestDTO);
    RoleResponseDTO patchRole(Long id, RoleRequestDTO requestDTO);
    RoleResponseDTO deleteRole(Long id);

}
