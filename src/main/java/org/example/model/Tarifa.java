package org.example.model;

import jakarta.persistence.*;
import org.example.model.enums.TipoTarifa;

import java.math.BigDecimal;

@Entity
@Table(name = "tarifas", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"tipo_tarifa", "tipo_vehiculo_id"})
})
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_tarifa", nullable = false, length = 20)
    private TipoTarifa tipoTarifa; // HORA, DIA, MENSUALIDAD

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_vehiculo_id", nullable = false)
    private TipoVehiculo tipoVehiculo;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(nullable = false)
    private Boolean activa = true;

    public Tarifa() {}

    public Tarifa(TipoTarifa tipoTarifa, TipoVehiculo tipoVehiculo, BigDecimal monto) {
        this.tipoTarifa = tipoTarifa;
        this.tipoVehiculo = tipoVehiculo;
        this.monto = monto;
        this.activa = true;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoTarifa getTipoTarifa() {
        return tipoTarifa;
    }

    public void setTipoTarifa(TipoTarifa tipoTarifa) {
        this.tipoTarifa = tipoTarifa;
    }

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }
}

