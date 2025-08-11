package com.dohimenezg.auth.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dohimenezg.auth.dto.request.RoleRequestDTO;
import com.dohimenezg.auth.dto.response.RoleResponseDTO;
import com.dohimenezg.auth.service.IRoleService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth/roles")
public class RoleController {
    
    private final IRoleService roleService;

    @Autowired
    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    // @PreAuthorize("isAuthenticated()")  TODO: SEGURIDAD Cualquier usuario autenticado puede ver los roles
    public ResponseEntity<List<RoleResponseDTO>> getAllRoles() {
        List<RoleResponseDTO> roles = roleService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> getRoleById(@PathVariable("id") Long id) {
        RoleResponseDTO role = roleService.getRoleById(id);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @PostMapping
    // @PreAuthorize("hasAuthority('ADMIN')") // TODO: SEGURIDAD Solo los administradores pueden crear roles
    public ResponseEntity<RoleResponseDTO> createRole(@Valid @RequestBody RoleRequestDTO requestDTO) {
        RoleResponseDTO createdRole = roleService.createRole(requestDTO);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    // @PreAuthorize("hasAuthority('ADMIN')") // TODO: SEGURIDAD Solo los administradores pueden actualizar roles
    public ResponseEntity<RoleResponseDTO> updateRole(@PathVariable("id") Long id, @Valid @RequestBody RoleRequestDTO requestDTO) {
        RoleResponseDTO updatedRole = roleService.updateRole(id, requestDTO);
        return new ResponseEntity<>(updatedRole, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    // @PreAuthorize("hasAuthority('ADMIN')") // TODO: SEGURIDAD Solo los administradores pueden actualizar roles
    public ResponseEntity<RoleResponseDTO> patchRole(@PathVariable("id") Long id, @RequestBody RoleRequestDTO requestDTO) {
        RoleResponseDTO patchedRole = roleService.patchRole(id, requestDTO);
        return new ResponseEntity<>(patchedRole, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    // @PreAuthorize("hasAuthority('ADMIN')") // TODO: SEGURIDAD Solo los administradores pueden eliminar roles
    public ResponseEntity<RoleResponseDTO> deleteRole(@PathVariable("id") Long id) {
        RoleResponseDTO deletedRole = roleService.deleteRole(id);
        return new ResponseEntity<>(deletedRole, HttpStatus.OK);
    }

}
