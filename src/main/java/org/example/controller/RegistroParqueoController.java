package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.dto.ConsultaCostoResponseDTO;
import org.example.dto.IngresoVehiculoDTO;
import org.example.model.RegistroParqueo;
import org.example.service.RegistroParqueoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parqueo")
@Tag(name = "Registro de Parqueo", description = "Ingreso y salida de vehículos, consulta de costos")
public class RegistroParqueoController {

    private final RegistroParqueoService registroParqueoService;

    public RegistroParqueoController(RegistroParqueoService registroParqueoService) {
        this.registroParqueoService = registroParqueoService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los registros de parqueo")
    public ResponseEntity<List<RegistroParqueo>> listarTodos() {
        return ResponseEntity.ok(registroParqueoService.listarTodos());
    }

    @GetMapping("/activos")
    @Operation(summary = "Listar vehículos actualmente en el parqueadero")
    public ResponseEntity<List<RegistroParqueo>> listarActivos() {
        return ResponseEntity.ok(registroParqueoService.listarActivos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un registro de parqueo por ID")
    public ResponseEntity<RegistroParqueo> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(registroParqueoService.obtenerPorId(id));
    }

    @PostMapping("/ingreso")
    @Operation(summary = "Registrar ingreso de un vehículo",
            description = "El vehículo debe estar previamente registrado en el sistema. No puede ingresar si ya tiene un registro activo (ya está dentro).")
    public ResponseEntity<RegistroParqueo> ingresarVehiculo(@Valid @RequestBody IngresoVehiculoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(registroParqueoService.ingresarVehiculo(dto));
    }

    @PostMapping("/salida/{placa}")
    @Operation(summary = "Registrar salida de un vehículo",
            description = "Finaliza el registro activo del vehículo. Calcula el costo por horas. Si tiene mensualidad activa, el costo es $0.")
    public ResponseEntity<RegistroParqueo> gestionarSalida(@PathVariable String placa) {
        return ResponseEntity.ok(registroParqueoService.gestionarSalida(placa));
    }

    @GetMapping("/consulta/placa/{placa}")
    @Operation(summary = "Consultar tiempo y costo acumulado por placa",
            description = "Devuelve el tiempo transcurrido y el costo acumulado de un vehículo que está actualmente en el parqueadero.")
    public ResponseEntity<ConsultaCostoResponseDTO> consultarPorPlaca(@PathVariable String placa) {
        return ResponseEntity.ok(registroParqueoService.consultarCostoPorPlaca(placa));
    }

    @GetMapping("/consulta/cliente/{clienteId}")
    @Operation(summary = "Consultar vehículos activos de un cliente",
            description = "Devuelve la lista de vehículos del cliente que están actualmente en el parqueadero, con tiempo y costo.")
    public ResponseEntity<List<ConsultaCostoResponseDTO>> consultarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(registroParqueoService.consultarCostoPorCliente(clienteId));
    }
}

