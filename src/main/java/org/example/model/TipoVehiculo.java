package org.example.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "tipos_vehiculo")
public class TipoVehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String nombre; // CARRO, MOTO, CAMIONETA, BICICLETA...

    @Column(length = 200)
    private String descripcion;

    @OneToMany(mappedBy = "tipoVehiculo")
    private List<Vehiculo> vehiculos;

    @OneToMany(mappedBy = "tipoVehiculo")
    private List<Tarifa> tarifas;

    public TipoVehiculo() {}

    public TipoVehiculo(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    public List<Tarifa> getTarifas() {
        return tarifas;
    }

    public void setTarifas(List<Tarifa> tarifas) {
        this.tarifas = tarifas;
    }
}

