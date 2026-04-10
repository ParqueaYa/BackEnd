package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO para crear/actualizar un cliente")
public class ClienteDTO {

    @Schema(description = "Cédula del cliente", example = "1234567890")
    @NotBlank(message = "La cédula es obligatoria")
    @Size(max = 20)
    private String cedula;

    @Schema(description = "Nombre del cliente", example = "Juan")
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 80)
    private String nombre;

    @Schema(description = "Apellido del cliente", example = "Pérez")
    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 80)
    private String apellido;

    @Schema(description = "Teléfono del cliente", example = "3001234567")
    @Size(max = 20)
    private String telefono;

    @Schema(description = "Email del cliente", example = "juan@email.com")
    @Email(message = "Email no válido")
    @Size(max = 100)
    private String email;

    @Schema(description = "Dirección del cliente", example = "Calle 123 #45-67")
    @Size(max = 200)
    private String direccion;

    public ClienteDTO() {}

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
}

