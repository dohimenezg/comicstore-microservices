package com.dohimenezg.auth.service.impl;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dohimenezg.auth.dto.request.RoleRequestDTO;
import com.dohimenezg.auth.dto.response.RoleResponseDTO;
import com.dohimenezg.auth.model.Role;
import com.dohimenezg.auth.repository.IRoleRepository;
import com.dohimenezg.auth.service.IRoleService;
import com.dohimenezg.auth.exception.ResourceNotFoundException;

@Service
public class RoleServiceImpl implements IRoleService {

    private final IRoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponseDTO> getAllRoles() {
        return roleRepository
            .findAll()
            .stream()
            .map(RoleResponseDTO::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponseDTO getRoleById(Long id) {
        Role role = findRoleByIdOrThrow(id);
        return RoleResponseDTO.fromEntity(role);
    }

    @Override
    @Transactional
    public RoleResponseDTO createRole(RoleRequestDTO requestDTO) {
        roleRepository.findByName(requestDTO.getName()).ifPresent(r -> {
            throw new DataIntegrityViolationException("Role with name '" + requestDTO.getName() + "' already exists.");
        });
        Role newRole = new Role();
        newRole.setName(requestDTO.getName());
        newRole.setDescription(requestDTO.getDescription());
        Role savedRole = roleRepository.save(newRole);
        return RoleResponseDTO.fromEntity(savedRole);
    }

    @Override
    @Transactional
    public RoleResponseDTO updateRole(Long id, RoleRequestDTO requestDTO) {
        Role existingRole = findRoleByIdOrThrow(id);
        validateRoleNameUniqueness(requestDTO.getName(), existingRole.getId());
        existingRole.setName(requestDTO.getName());
        existingRole.setDescription(requestDTO.getDescription());
        Role updatedRole = roleRepository.save(existingRole);
        return RoleResponseDTO.fromEntity(updatedRole);
    }

    @Override
    @Transactional
    public RoleResponseDTO patchRole(Long id, RoleRequestDTO requestDTO) {
        if (requestDTO.getName() == null && requestDTO.getDescription() == null) {
            throw new IllegalArgumentException("Patch request cannot be empty. At least one field must be provided.");
        }
        Role existingRole = findRoleByIdOrThrow(id);
        if (requestDTO.getName() != null) {
            validateRoleNameUniqueness(requestDTO.getName(), existingRole.getId());
            existingRole.setName(requestDTO.getName());
        }
        if (requestDTO.getDescription() != null) {
            existingRole.setDescription(requestDTO.getDescription());
        }
        Role patchedRole = roleRepository.save(existingRole);
        return RoleResponseDTO.fromEntity(patchedRole);
    }
    
    @Override
    @Transactional
    public RoleResponseDTO deleteRole(Long id) {
        Role existingRole = findRoleByIdOrThrow(id);
        roleRepository.deleteById(id);
        return RoleResponseDTO.fromEntity(existingRole);
    }

    private void validateRoleNameUniqueness(String name, Long currentRoleId) {
        roleRepository.findByName(name).ifPresent(roleWithSameName -> {
            if (!roleWithSameName.getId().equals(currentRoleId)) {
                throw new DataIntegrityViolationException("The name '" + name + "' is already in use by another role.");
            }
        });
    }

    private Role findRoleByIdOrThrow(Long id) {
        return roleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
    }

}
