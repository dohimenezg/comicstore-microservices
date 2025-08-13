package com.dohimenezg.auth.dto.request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequestDTO {
    
    @NotBlank(message = "El email no puede estar vacío.")
    @Email(message = "El formato del email no es válido.")
    private String email;
    
    @NotBlank(message = "La contraseña no puede estar vacía.")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres.")
    private String password;
    
    @NotBlank(message = "El nombre no puede estar vacío.")
    private String firstName;
    
    @NotBlank(message = "El apellido no puede estar vacío.")
    private String lastName;

    @NotBlank(message = "La dirección no puede estar vacía.")
    private String address;
    
    private String bio;

}
