package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "DTO para crear una mensualidad")
public class MensualidadDTO {

    @Schema(description = "ID del cliente", example = "1")
    @NotNull(message = "El cliente es obligatorio")
    private Long clienteId;

    @Schema(description = "ID del vehículo", example = "1")
    @NotNull(message = "El vehículo es obligatorio")
    private Long vehiculoId;

    @Schema(description = "ID de la tarifa mensual a aplicar", example = "1")
    @NotNull(message = "La tarifa es obligatoria")
    private Long tarifaId;

    @Schema(description = "Fecha de inicio de la mensualidad", example = "2026-04-10")
    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @Schema(description = "Fecha de fin de la mensualidad", example = "2026-05-10")
    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate fechaFin;

    public MensualidadDTO() {}

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public Long getVehiculoId() { return vehiculoId; }
    public void setVehiculoId(Long vehiculoId) { this.vehiculoId = vehiculoId; }
    public Long getTarifaId() { return tarifaId; }
    public void setTarifaId(Long tarifaId) { this.tarifaId = tarifaId; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
}

