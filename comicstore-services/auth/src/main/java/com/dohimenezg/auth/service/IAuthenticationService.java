package com.dohimenezg.auth.service;
import com.dohimenezg.auth.dto.request.LoginRequestDTO;
import com.dohimenezg.auth.dto.request.UserRegisterRequestDTO;
import com.dohimenezg.auth.dto.response.AuthResponseDTO;

public interface IAuthenticationService {

    AuthResponseDTO register(UserRegisterRequestDTO requestDTO);
    AuthResponseDTO login(LoginRequestDTO requestDTO);

}
