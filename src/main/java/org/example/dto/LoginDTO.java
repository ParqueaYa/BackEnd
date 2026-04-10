package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO para login con usuario y contraseña")
public class LoginDTO {

    @Schema(description = "Email del usuario", example = "admin@parqueaya.com")
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Email no válido")
    private String email;

    @Schema(description = "Contraseña del usuario", example = "miPassword123")
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    public LoginDTO() {}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

