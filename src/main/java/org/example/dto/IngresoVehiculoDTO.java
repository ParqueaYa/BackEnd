package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO para registrar ingreso de vehículo al parqueadero")
public class IngresoVehiculoDTO {

    @Schema(description = "Placa del vehículo", example = "ABC123")
    @NotBlank(message = "La placa es obligatoria")
    private String placa;

    @Schema(description = "Observaciones del ingreso", example = "Vehículo con rayón en puerta derecha")
    private String observaciones;

    public IngresoVehiculoDTO() {}

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}

