package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Schema(description = "DTO para crear/actualizar una tarifa")
public class TarifaDTO {

    @Schema(description = "Tipo de tarifa: HORA, DIA o MENSUALIDAD", example = "HORA")
    @NotNull(message = "El tipo de tarifa es obligatorio")
    private String tipoTarifa;

    @Schema(description = "ID del tipo de vehículo al que aplica", example = "1")
    @NotNull(message = "El tipo de vehículo es obligatorio")
    private Long tipoVehiculoId;

    @Schema(description = "Monto de la tarifa", example = "5000.00")
    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser positivo")
    private BigDecimal monto;

    @Schema(description = "Indica si la tarifa está activa", example = "true")
    private Boolean activa = true;

    public TarifaDTO() {}

    public String getTipoTarifa() { return tipoTarifa; }
    public void setTipoTarifa(String tipoTarifa) { this.tipoTarifa = tipoTarifa; }
    public Long getTipoVehiculoId() { return tipoVehiculoId; }
    public void setTipoVehiculoId(Long tipoVehiculoId) { this.tipoVehiculoId = tipoVehiculoId; }
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
    public Boolean getActiva() { return activa; }
    public void setActiva(Boolean activa) { this.activa = activa; }
}

