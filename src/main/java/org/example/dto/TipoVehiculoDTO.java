package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO para crear/actualizar un tipo de vehículo")
public class TipoVehiculoDTO {

    @Schema(description = "Nombre del tipo de vehículo", example = "CARRO")
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50)
    private String nombre;

    @Schema(description = "Descripción del tipo de vehículo", example = "Vehículo de cuatro ruedas")
    @Size(max = 200)
    private String descripcion;

    public TipoVehiculoDTO() {}

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}

