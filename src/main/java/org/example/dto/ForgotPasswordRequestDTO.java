package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO para solicitar la recuperación de contraseña")
public class ForgotPasswordRequestDTO {

    @Schema(description = "Email del usuario", example = "operador@parqueaya.com")
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Email no válido")
    private String email;

    public ForgotPasswordRequestDTO() {}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
