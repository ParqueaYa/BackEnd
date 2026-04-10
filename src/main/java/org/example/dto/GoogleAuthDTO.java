package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO para autenticación con Google. El frontend envía el ID token obtenido de Google Sign-In.")
public class GoogleAuthDTO {

    @Schema(description = "ID Token de Google obtenido en el frontend", example = "eyJhbGciOiJSUzI1NiIs...")
    @NotBlank(message = "El token de Google es obligatorio")
    private String idToken;

    public GoogleAuthDTO() {}

    public String getIdToken() { return idToken; }
    public void setIdToken(String idToken) { this.idToken = idToken; }
}

