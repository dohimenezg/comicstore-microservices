package com.dohimenezg.auth.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dohimenezg.auth.dto.request.UserUpdateRequestDTO;
import com.dohimenezg.auth.dto.response.UserResponseDTO;
import com.dohimenezg.auth.exception.ResourceNotFoundException;
import com.dohimenezg.auth.model.User;
import com.dohimenezg.auth.repository.IUserRepository;
import com.dohimenezg.auth.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;

    @Autowired
    public UserServiceImpl (IUserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository
            .findAll()
            .stream()
            .map(UserResponseDTO::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        User user = findUserByIdOrThrow(id);
        return UserResponseDTO.fromEntity(user);
    }

    @Override
    @Transactional
    public UserResponseDTO updateUser(Long id, UserUpdateRequestDTO requestDTO) {
        User existingUser = findUserByIdOrThrow(id);
        existingUser.setFirstName(requestDTO.getFirstName());
        existingUser.setLastName(requestDTO.getLastName());
        existingUser.setAddress(requestDTO.getAddress());
        existingUser.setBio(requestDTO.getBio());
        existingUser.setProfilePictureId(requestDTO.getProfilePictureId());
        User updatedUser = userRepository.save(existingUser);
        return UserResponseDTO.fromEntity(updatedUser);
    }

    @Override
    @Transactional
    public UserResponseDTO patchUser(Long id, UserUpdateRequestDTO requestDTO) {
        if (requestDTO.getFirstName() == null && requestDTO.getLastName() == null &&
            requestDTO.getAddress() == null && requestDTO.getBio() == null &&
            requestDTO.getProfilePictureId() == null) {
            throw new IllegalArgumentException("Patch request cannot be empty. At least one field must be provided to update.");
        }
        User existingUser = findUserByIdOrThrow(id);
        if (requestDTO.getFirstName() != null) {
            existingUser.setFirstName(requestDTO.getFirstName());
        }
        if (requestDTO.getLastName() != null) {
            existingUser.setLastName(requestDTO.getLastName());
        }
        if (requestDTO.getAddress() != null) {
            existingUser.setAddress(requestDTO.getAddress());
        }
        if (requestDTO.getBio() != null) {
            existingUser.setBio(requestDTO.getBio());
        }
        if (requestDTO.getProfilePictureId() != null) {
            existingUser.setProfilePictureId(requestDTO.getProfilePictureId());
        }
        User patchedUser = userRepository.save(existingUser);
        return UserResponseDTO.fromEntity(patchedUser);
    }

    @Override
    @Transactional
    public UserResponseDTO deleteUser(Long id) {
        User existingUser = findUserByIdOrThrow(id);
        userRepository.deleteById(id);
        return UserResponseDTO.fromEntity(existingUser);
    }

    private User findUserByIdOrThrow(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
    
}
