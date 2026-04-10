package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO para generar una factura")
public class GenerarFacturaDTO {

    @Schema(description = "ID del registro de parqueo (para factura por hora/día)", example = "1")
    private Long registroParqueoId;

    @Schema(description = "ID de la mensualidad (para factura de mensualidad)", example = "1")
    private Long mensualidadId;

    @Schema(description = "ID del cliente", example = "1")
    @NotNull(message = "El cliente es obligatorio")
    private Long clienteId;

    @Schema(description = "ID del vehículo", example = "1")
    @NotNull(message = "El vehículo es obligatorio")
    private Long vehiculoId;

    @Schema(description = "Detalle adicional de la factura", example = "Parqueo por 3 horas")
    private String detalle;

    public GenerarFacturaDTO() {}

    public Long getRegistroParqueoId() { return registroParqueoId; }
    public void setRegistroParqueoId(Long registroParqueoId) { this.registroParqueoId = registroParqueoId; }
    public Long getMensualidadId() { return mensualidadId; }
    public void setMensualidadId(Long mensualidadId) { this.mensualidadId = mensualidadId; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public Long getVehiculoId() { return vehiculoId; }
    public void setVehiculoId(Long vehiculoId) { this.vehiculoId = vehiculoId; }
    public String getDetalle() { return detalle; }
    public void setDetalle(String detalle) { this.detalle = detalle; }
}

