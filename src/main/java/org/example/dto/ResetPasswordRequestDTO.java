package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO para restablecer la contraseña usando un token")
public class ResetPasswordRequestDTO {

    @Schema(description = "Token de recuperación recibido por el usuario", example = "a1b2c3d4-e5f6-...")
    @NotBlank(message = "El token es obligatorio")
    private String token;

    @Schema(description = "Nueva contraseña del usuario", example = "nuevaClave123")
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String newPassword;

    public ResetPasswordRequestDTO() {}

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
}
