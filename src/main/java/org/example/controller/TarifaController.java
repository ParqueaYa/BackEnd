package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.dto.TarifaDTO;
import org.example.model.Tarifa;
import org.example.service.TarifaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarifas")
@Tag(name = "Tarifas", description = "Configuración de tarifas por hora, día y mensualidad")
public class TarifaController {

    private final TarifaService tarifaService;

    public TarifaController(TarifaService tarifaService) {
        this.tarifaService = tarifaService;
    }

    @GetMapping
    @Operation(summary = "Listar todas las tarifas")
    public ResponseEntity<List<Tarifa>> listarTodas() {
        return ResponseEntity.ok(tarifaService.listarTodas());
    }

    @GetMapping("/activas")
    @Operation(summary = "Listar solo las tarifas activas")
    public ResponseEntity<List<Tarifa>> listarActivas() {
        return ResponseEntity.ok(tarifaService.listarActivas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una tarifa por ID")
    public ResponseEntity<Tarifa> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tarifaService.obtenerPorId(id));
    }

    @GetMapping("/tipo/{tipoTarifa}")
    @Operation(summary = "Listar tarifas por tipo (HORA, DIA, MENSUALIDAD)")
    public ResponseEntity<List<Tarifa>> listarPorTipo(@PathVariable String tipoTarifa) {
        return ResponseEntity.ok(tarifaService.listarPorTipo(tipoTarifa));
    }

    @PostMapping
    @Operation(summary = "Crear una nueva tarifa", description = "Crea una tarifa para un tipo de vehículo. No puede haber dos tarifas activas del mismo tipo para el mismo tipo de vehículo.")
    public ResponseEntity<Tarifa> crear(@Valid @RequestBody TarifaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tarifaService.crear(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una tarifa existente")
    public ResponseEntity<Tarifa> actualizar(@PathVariable Long id, @Valid @RequestBody TarifaDTO dto) {
        return ResponseEntity.ok(tarifaService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una tarifa")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tarifaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

