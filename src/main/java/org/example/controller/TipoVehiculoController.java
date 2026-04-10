package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.dto.TipoVehiculoDTO;
import org.example.model.TipoVehiculo;
import org.example.service.TipoVehiculoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-vehiculo")
@Tag(name = "Tipos de Vehículo", description = "CRUD de tipos de vehículo (CARRO, MOTO, CAMIONETA, etc.)")
public class TipoVehiculoController {

    private final TipoVehiculoService tipoVehiculoService;

    public TipoVehiculoController(TipoVehiculoService tipoVehiculoService) {
        this.tipoVehiculoService = tipoVehiculoService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los tipos de vehículo")
    public ResponseEntity<List<TipoVehiculo>> listarTodos() {
        return ResponseEntity.ok(tipoVehiculoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un tipo de vehículo por ID")
    public ResponseEntity<TipoVehiculo> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tipoVehiculoService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo tipo de vehículo")
    public ResponseEntity<TipoVehiculo> crear(@Valid @RequestBody TipoVehiculoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoVehiculoService.crear(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un tipo de vehículo existente")
    public ResponseEntity<TipoVehiculo> actualizar(@PathVariable Long id, @Valid @RequestBody TipoVehiculoDTO dto) {
        return ResponseEntity.ok(tipoVehiculoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un tipo de vehículo")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tipoVehiculoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

