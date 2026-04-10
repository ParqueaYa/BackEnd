package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Respuesta con la consulta de tiempo y costo de un vehículo en el parqueadero")
public class ConsultaCostoResponseDTO {

    @Schema(description = "Placa del vehículo")
    private String placa;

    @Schema(description = "Tipo de vehículo")
    private String tipoVehiculo;

    @Schema(description = "Nombre del cliente (si aplica)")
    private String nombreCliente;

    @Schema(description = "Fecha y hora de ingreso")
    private LocalDateTime fechaIngreso;

    @Schema(description = "Tiempo transcurrido en minutos")
    private long minutosTranscurridos;

    @Schema(description = "Tiempo transcurrido legible (ej: 2h 30min)")
    private String tiempoTranscurrido;

    @Schema(description = "Costo acumulado")
    private BigDecimal costoAcumulado;

    @Schema(description = "Tarifa aplicada por hora")
    private BigDecimal tarifaHora;

    @Schema(description = "Indica si tiene mensualidad activa")
    private boolean tieneMensualidad;

    public ConsultaCostoResponseDTO() {}

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public String getTipoVehiculo() { return tipoVehiculo; }
    public void setTipoVehiculo(String tipoVehiculo) { this.tipoVehiculo = tipoVehiculo; }
    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }
    public LocalDateTime getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDateTime fechaIngreso) { this.fechaIngreso = fechaIngreso; }
    public long getMinutosTranscurridos() { return minutosTranscurridos; }
    public void setMinutosTranscurridos(long minutosTranscurridos) { this.minutosTranscurridos = minutosTranscurridos; }
    public String getTiempoTranscurrido() { return tiempoTranscurrido; }
    public void setTiempoTranscurrido(String tiempoTranscurrido) { this.tiempoTranscurrido = tiempoTranscurrido; }
    public BigDecimal getCostoAcumulado() { return costoAcumulado; }
    public void setCostoAcumulado(BigDecimal costoAcumulado) { this.costoAcumulado = costoAcumulado; }
    public BigDecimal getTarifaHora() { return tarifaHora; }
    public void setTarifaHora(BigDecimal tarifaHora) { this.tarifaHora = tarifaHora; }
    public boolean isTieneMensualidad() { return tieneMensualidad; }
    public void setTieneMensualidad(boolean tieneMensualidad) { this.tieneMensualidad = tieneMensualidad; }
}

