package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO para registrar un nuevo usuario local")
public class RegistroUsuarioDTO {

    @Schema(description = "Email del usuario", example = "operador@parqueaya.com")
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Email no válido")
    private String email;

    @Schema(description = "Contraseña (mínimo 6 caracteres)", example = "miPassword123")
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    @Schema(description = "Nombre del usuario", example = "Carlos")
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Schema(description = "Apellido del usuario", example = "García")
    private String apellido;

    @Schema(description = "Rol del usuario: ADMIN, OPERADOR o CLIENTE", example = "OPERADOR")
    private String rol;

    public RegistroUsuarioDTO() {}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}

