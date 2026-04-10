package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.dto.GenerarFacturaDTO;
import org.example.model.Factura;
import org.example.service.FacturaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@Tag(name = "Facturas", description = "Generación y consulta de facturas")
public class FacturaController {

    private final FacturaService facturaService;

    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @GetMapping
    @Operation(summary = "Listar todas las facturas")
    public ResponseEntity<List<Factura>> listarTodas() {
        return ResponseEntity.ok(facturaService.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una factura por ID")
    public ResponseEntity<Factura> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(facturaService.obtenerPorId(id));
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar facturas de un cliente")
    public ResponseEntity<List<Factura>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(facturaService.listarPorCliente(clienteId));
    }

    @GetMapping("/placa/{placa}")
    @Operation(summary = "Listar facturas por placa de vehículo")
    public ResponseEntity<List<Factura>> listarPorPlaca(@PathVariable String placa) {
        return ResponseEntity.ok(facturaService.listarPorPlaca(placa));
    }

    @PostMapping
    @Operation(summary = "Generar una factura",
            description = "Genera una factura. Requiere cliente y vehículo. Opcionalmente se puede asociar a un registro de parqueo o a una mensualidad.")
    public ResponseEntity<Factura> generarFactura(@Valid @RequestBody GenerarFacturaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facturaService.generarFactura(dto));
    }
}

