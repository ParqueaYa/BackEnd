package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta de autenticación con el JWT token y datos del usuario")
public class AuthResponseDTO {

    @Schema(description = "JWT token para autorización en los headers")
    private String token;

    @Schema(description = "Tipo de token", example = "Bearer")
    private String tipo = "Bearer";

    @Schema(description = "ID del usuario")
    private Long id;

    @Schema(description = "Email del usuario")
    private String email;

    @Schema(description = "Nombre completo del usuario")
    private String nombreCompleto;

    @Schema(description = "Rol del usuario")
    private String rol;

    @Schema(description = "Proveedor de autenticación: LOCAL o GOOGLE")
    private String authProvider;

    @Schema(description = "URL de la foto de perfil (Google)")
    private String fotoUrl;

    public AuthResponseDTO() {}

    public AuthResponseDTO(String token, Long id, String email, String nombreCompleto,
                           String rol, String authProvider, String fotoUrl) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
        this.authProvider = authProvider;
        this.fotoUrl = fotoUrl;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public String getAuthProvider() { return authProvider; }
    public void setAuthProvider(String authProvider) { this.authProvider = authProvider; }
    public String getFotoUrl() { return fotoUrl; }
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl; }
}

