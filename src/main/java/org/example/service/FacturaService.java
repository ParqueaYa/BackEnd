package org.example.service;

import org.example.dto.GenerarFacturaDTO;
import org.example.model.*;
import org.example.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class FacturaService {

    private final FacturaRepository facturaRepository;
    private final ClienteRepository clienteRepository;
    private final VehiculoRepository vehiculoRepository;
    private final RegistroParqueoRepository registroParqueoRepository;
    private final MensualidadRepository mensualidadRepository;

    private static final BigDecimal IVA_PORCENTAJE = new BigDecimal("0.19");

    public FacturaService(FacturaRepository facturaRepository,
                          ClienteRepository clienteRepository,
                          VehiculoRepository vehiculoRepository,
                          RegistroParqueoRepository registroParqueoRepository,
                          MensualidadRepository mensualidadRepository) {
        this.facturaRepository = facturaRepository;
        this.clienteRepository = clienteRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.registroParqueoRepository = registroParqueoRepository;
        this.mensualidadRepository = mensualidadRepository;
    }

    public List<Factura> listarTodas() {
        return facturaRepository.findAll();
    }

    public Factura obtenerPorId(Long id) {
        return facturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada con ID: " + id));
    }

    public List<Factura> listarPorCliente(Long clienteId) {
        return facturaRepository.findByClienteId(clienteId);
    }

    public List<Factura> listarPorPlaca(String placa) {
        return facturaRepository.findByVehiculoPlaca(placa.toUpperCase());
    }

    public Factura generarFactura(GenerarFacturaDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + dto.getClienteId()));

        Vehiculo vehiculo = vehiculoRepository.findById(dto.getVehiculoId())
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado con ID: " + dto.getVehiculoId()));

        BigDecimal subtotal = BigDecimal.ZERO;
        RegistroParqueo registro = null;
        Mensualidad mensualidad = null;

        if (dto.getRegistroParqueoId() != null) {
            registro = registroParqueoRepository.findById(dto.getRegistroParqueoId())
                    .orElseThrow(() -> new RuntimeException("Registro de parqueo no encontrado con ID: " + dto.getRegistroParqueoId()));
            if (registro.getTotalCobrado() != null) {
                subtotal = registro.getTotalCobrado();
            }
        }

        if (dto.getMensualidadId() != null) {
            mensualidad = mensualidadRepository.findById(dto.getMensualidadId())
                    .orElseThrow(() -> new RuntimeException("Mensualidad no encontrada con ID: " + dto.getMensualidadId()));
            subtotal = mensualidad.getMontoPagado();
        }

        BigDecimal iva = subtotal.multiply(IVA_PORCENTAJE).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = subtotal.add(iva).setScale(2, RoundingMode.HALF_UP);

        String numeroFactura = generarNumeroFactura();

        Factura factura = new Factura();
        factura.setNumeroFactura(numeroFactura);
        factura.setCliente(cliente);
        factura.setVehiculo(vehiculo);
        factura.setRegistroParqueo(registro);
        factura.setMensualidad(mensualidad);
        factura.setSubtotal(subtotal);
        factura.setIva(iva);
        factura.setTotal(total);
        factura.setDetalle(dto.getDetalle());

        return facturaRepository.save(factura);
    }

    private String generarNumeroFactura() {
        String prefix = "FAC-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-";
        long count = facturaRepository.count() + 1;
        return prefix + String.format("%05d", count);
    }
}

