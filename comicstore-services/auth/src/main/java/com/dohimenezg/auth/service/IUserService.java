package com.dohimenezg.auth.service;
import java.util.List;
import com.dohimenezg.auth.dto.request.UserUpdateRequestDTO;
import com.dohimenezg.auth.dto.response.UserResponseDTO;

public interface IUserService {
    
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getUserById(Long id);
    UserResponseDTO updateUser(Long id, UserUpdateRequestDTO requestDTO);
    UserResponseDTO patchUser(Long id, UserUpdateRequestDTO requestDTO);
    UserResponseDTO deleteUser(Long id);

}
