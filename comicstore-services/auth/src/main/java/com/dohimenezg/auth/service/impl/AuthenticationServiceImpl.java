package com.dohimenezg.auth.service.impl;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.dohimenezg.auth.dto.request.LoginRequestDTO;
import com.dohimenezg.auth.dto.request.UserRegisterRequestDTO;
import com.dohimenezg.auth.dto.response.AuthResponseDTO;
import com.dohimenezg.auth.model.Role;
import com.dohimenezg.auth.model.User;
import com.dohimenezg.auth.repository.IRoleRepository;
import com.dohimenezg.auth.repository.IUserRepository;
import com.dohimenezg.auth.service.IAuthenticationService;
import com.dohimenezg.auth.service.IJwtService;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final IJwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationServiceImpl (
            IUserRepository userRepository,
            IRoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            IJwtService jwtService,
            AuthenticationManager authenticationManager
        ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    // TODO: revisar la parte de los roles
    @Override
    public AuthResponseDTO register(UserRegisterRequestDTO requestDTO) {
        if (userRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
            throw new DataIntegrityViolationException("Email '" + requestDTO.getEmail() + "' is already taken.");
        }
        User user = new User();
        user.setEmail(requestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());
        user.setAddress(requestDTO.getAddress());
        user.setBio(requestDTO.getBio());
        user.setEnabled(true);
        Role defaultRole = roleRepository.findByName("CLIENT")
            .orElseThrow(() -> new IllegalStateException("Default role 'CLIENT' not found."));
        Set<Role> roles = new HashSet<>();
        roles.add(defaultRole);
        user.setRoles(roles);
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return new AuthResponseDTO(jwtToken);
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO requestDTO) {
        authenticationManager.authenticate (
            new UsernamePasswordAuthenticationToken (
                requestDTO.getEmail(),
                requestDTO.getPassword()
            )
        );
        User user = userRepository.findByEmail(requestDTO.getEmail())
            .orElseThrow(() -> new IllegalStateException("User not found after successful authentication."));
        String jwtToken = jwtService.generateToken(user);
        return new AuthResponseDTO(jwtToken);
    }
    
}
