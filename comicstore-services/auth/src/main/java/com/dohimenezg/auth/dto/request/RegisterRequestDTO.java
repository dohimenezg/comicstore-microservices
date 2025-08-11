package com.dohimenezg.auth.dto.request;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDTO {
    
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String bio;

}
