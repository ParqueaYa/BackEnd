package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.dto.VehiculoDTO;
import org.example.model.Vehiculo;
import org.example.service.VehiculoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
@Tag(name = "Vehículos", description = "CRUD de vehículos y asociación con clientes")
public class VehiculoController {

    private final VehiculoService vehiculoService;

    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los vehículos")
    public ResponseEntity<List<Vehiculo>> listarTodos() {
        return ResponseEntity.ok(vehiculoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un vehículo por ID")
    public ResponseEntity<Vehiculo> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vehiculoService.obtenerPorId(id));
    }

    @GetMapping("/placa/{placa}")
    @Operation(summary = "Obtener un vehículo por su placa")
    public ResponseEntity<Vehiculo> obtenerPorPlaca(@PathVariable String placa) {
        return ResponseEntity.ok(vehiculoService.obtenerPorPlaca(placa));
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar vehículos de un cliente")
    public ResponseEntity<List<Vehiculo>> obtenerPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(vehiculoService.obtenerPorCliente(clienteId));
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo vehículo", description = "Registra un vehículo en el sistema. Si se envía clienteId, se asocia al cliente.")
    public ResponseEntity<Vehiculo> crear(@Valid @RequestBody VehiculoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculoService.crear(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un vehículo existente")
    public ResponseEntity<Vehiculo> actualizar(@PathVariable Long id, @Valid @RequestBody VehiculoDTO dto) {
        return ResponseEntity.ok(vehiculoService.actualizar(id, dto));
    }

    @PatchMapping("/{vehiculoId}/asociar-cliente/{clienteId}")
    @Operation(summary = "Asociar un vehículo a un cliente", description = "Asocia un vehículo existente a un cliente para modo mensualidad")
    public ResponseEntity<Vehiculo> asociarCliente(@PathVariable Long vehiculoId, @PathVariable Long clienteId) {
        return ResponseEntity.ok(vehiculoService.asociarCliente(vehiculoId, clienteId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un vehículo")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        vehiculoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

