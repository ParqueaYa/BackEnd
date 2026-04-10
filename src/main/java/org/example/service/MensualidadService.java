package org.example.service;

import org.example.dto.MensualidadDTO;
import org.example.model.Cliente;
import org.example.model.Mensualidad;
import org.example.model.Tarifa;
import org.example.model.Vehiculo;
import org.example.model.enums.EstadoMensualidad;
import org.example.model.enums.TipoTarifa;
import org.example.repository.ClienteRepository;
import org.example.repository.MensualidadRepository;
import org.example.repository.TarifaRepository;
import org.example.repository.VehiculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MensualidadService {

    private final MensualidadRepository mensualidadRepository;
    private final ClienteRepository clienteRepository;
    private final VehiculoRepository vehiculoRepository;
    private final TarifaRepository tarifaRepository;

    public MensualidadService(MensualidadRepository mensualidadRepository,
                              ClienteRepository clienteRepository,
                              VehiculoRepository vehiculoRepository,
                              TarifaRepository tarifaRepository) {
        this.mensualidadRepository = mensualidadRepository;
        this.clienteRepository = clienteRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.tarifaRepository = tarifaRepository;
    }

    public List<Mensualidad> listarTodas() {
        return mensualidadRepository.findAll();
    }

    public Mensualidad obtenerPorId(Long id) {
        return mensualidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mensualidad no encontrada con ID: " + id));
    }

    public List<Mensualidad> listarPorCliente(Long clienteId) {
        return mensualidadRepository.findByClienteId(clienteId);
    }

    public List<Mensualidad> listarPorEstado(String estado) {
        EstadoMensualidad estadoEnum = EstadoMensualidad.valueOf(estado.toUpperCase());
        return mensualidadRepository.findByEstado(estadoEnum);
    }

    public Mensualidad crear(MensualidadDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + dto.getClienteId()));

        Vehiculo vehiculo = vehiculoRepository.findById(dto.getVehiculoId())
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado con ID: " + dto.getVehiculoId()));

        Tarifa tarifa = tarifaRepository.findById(dto.getTarifaId())
                .orElseThrow(() -> new RuntimeException("Tarifa no encontrada con ID: " + dto.getTarifaId()));

        // Verificar que la tarifa sea de tipo MENSUALIDAD
        if (tarifa.getTipoTarifa() != TipoTarifa.MENSUALIDAD) {
            throw new RuntimeException("La tarifa seleccionada no es de tipo MENSUALIDAD");
        }

        // Verificar que el vehículo esté asociado al cliente
        if (vehiculo.getCliente() == null || !vehiculo.getCliente().getId().equals(cliente.getId())) {
            throw new RuntimeException("El vehículo no está asociado al cliente indicado. "
                    + "Primero debe asociar el vehículo al cliente.");
        }

        if (dto.getFechaFin().isBefore(dto.getFechaInicio())) {
            throw new RuntimeException("La fecha de fin no puede ser anterior a la fecha de inicio");
        }

        Mensualidad mensualidad = new Mensualidad();
        mensualidad.setCliente(cliente);
        mensualidad.setVehiculo(vehiculo);
        mensualidad.setTarifa(tarifa);
        mensualidad.setFechaInicio(dto.getFechaInicio());
        mensualidad.setFechaFin(dto.getFechaFin());
        mensualidad.setMontoPagado(tarifa.getMonto());

        return mensualidadRepository.save(mensualidad);
    }

    public Mensualidad cancelar(Long id) {
        Mensualidad mensualidad = obtenerPorId(id);
        mensualidad.setEstado(EstadoMensualidad.CANCELADA);
        return mensualidadRepository.save(mensualidad);
    }
}

