package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO para crear/actualizar un vehículo")
public class VehiculoDTO {

    @Schema(description = "Placa del vehículo", example = "ABC123")
    @NotBlank(message = "La placa es obligatoria")
    @Size(max = 10)
    private String placa;

    @Schema(description = "ID del tipo de vehículo", example = "1")
    @NotNull(message = "El tipo de vehículo es obligatorio")
    private Long tipoVehiculoId;

    @Schema(description = "Marca del vehículo", example = "Toyota")
    @Size(max = 50)
    private String marca;

    @Schema(description = "Color del vehículo", example = "Rojo")
    @Size(max = 30)
    private String color;

    @Schema(description = "Modelo del vehículo", example = "Corolla 2023")
    @Size(max = 50)
    private String modelo;

    @Schema(description = "ID del cliente (opcional, para asociar mensualidad)", example = "1")
    private Long clienteId;

    public VehiculoDTO() {}

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public Long getTipoVehiculoId() { return tipoVehiculoId; }
    public void setTipoVehiculoId(Long tipoVehiculoId) { this.tipoVehiculoId = tipoVehiculoId; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
}

