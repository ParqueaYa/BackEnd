package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.dto.MensualidadDTO;
import org.example.model.Mensualidad;
import org.example.service.MensualidadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mensualidades")
@Tag(name = "Mensualidades", description = "Gestión de mensualidades de parqueo")
public class MensualidadController {

    private final MensualidadService mensualidadService;

    public MensualidadController(MensualidadService mensualidadService) {
        this.mensualidadService = mensualidadService;
    }

    @GetMapping
    @Operation(summary = "Listar todas las mensualidades")
    public ResponseEntity<List<Mensualidad>> listarTodas() {
        return ResponseEntity.ok(mensualidadService.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una mensualidad por ID")
    public ResponseEntity<Mensualidad> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(mensualidadService.obtenerPorId(id));
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar mensualidades de un cliente")
    public ResponseEntity<List<Mensualidad>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(mensualidadService.listarPorCliente(clienteId));
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Listar mensualidades por estado (ACTIVA, VENCIDA, CANCELADA)")
    public ResponseEntity<List<Mensualidad>> listarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(mensualidadService.listarPorEstado(estado));
    }

    @PostMapping
    @Operation(summary = "Crear una nueva mensualidad",
            description = "Requiere cliente, vehículo asociado al cliente, y una tarifa de tipo MENSUALIDAD.")
    public ResponseEntity<Mensualidad> crear(@Valid @RequestBody MensualidadDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mensualidadService.crear(dto));
    }

    @PatchMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar una mensualidad")
    public ResponseEntity<Mensualidad> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(mensualidadService.cancelar(id));
    }
}

