package com.dohimenezg.auth.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequestDTO {

    @NotBlank(message = "El nombre del rol no puede estar vacío.")
    @Size(max = 250, message = "El nombre del rol no puede superar los 250 caracteres.")
    private String name;

    @Size(max = 1000, message = "La descripción no puede superar los 1000 caracteres.")
    private String description;

}