package org.example.service;

import org.example.dto.VehiculoDTO;
import org.example.model.Cliente;
import org.example.model.TipoVehiculo;
import org.example.model.Vehiculo;
import org.example.repository.ClienteRepository;
import org.example.repository.TipoVehiculoRepository;
import org.example.repository.VehiculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final TipoVehiculoRepository tipoVehiculoRepository;
    private final ClienteRepository clienteRepository;

    public VehiculoService(VehiculoRepository vehiculoRepository,
                           TipoVehiculoRepository tipoVehiculoRepository,
                           ClienteRepository clienteRepository) {
        this.vehiculoRepository = vehiculoRepository;
        this.tipoVehiculoRepository = tipoVehiculoRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<Vehiculo> listarTodos() {
        return vehiculoRepository.findAll();
    }

    public Vehiculo obtenerPorId(Long id) {
        return vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado con ID: " + id));
    }

    public Vehiculo obtenerPorPlaca(String placa) {
        return vehiculoRepository.findByPlaca(placa.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado con placa: " + placa));
    }

    public List<Vehiculo> obtenerPorCliente(Long clienteId) {
        return vehiculoRepository.findByClienteId(clienteId);
    }

    public Vehiculo crear(VehiculoDTO dto) {
        if (vehiculoRepository.existsByPlaca(dto.getPlaca().toUpperCase())) {
            throw new RuntimeException("Ya existe un vehículo con la placa: " + dto.getPlaca());
        }

        TipoVehiculo tipoVehiculo = tipoVehiculoRepository.findById(dto.getTipoVehiculoId())
                .orElseThrow(() -> new RuntimeException("Tipo de vehículo no encontrado con ID: " + dto.getTipoVehiculoId()));

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPlaca(dto.getPlaca().toUpperCase());
        vehiculo.setTipoVehiculo(tipoVehiculo);
        vehiculo.setMarca(dto.getMarca());
        vehiculo.setColor(dto.getColor());
        vehiculo.setModelo(dto.getModelo());

        if (dto.getClienteId() != null) {
            Cliente cliente = clienteRepository.findById(dto.getClienteId())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + dto.getClienteId()));
            vehiculo.setCliente(cliente);
        }

        return vehiculoRepository.save(vehiculo);
    }

    public Vehiculo actualizar(Long id, VehiculoDTO dto) {
        Vehiculo vehiculo = obtenerPorId(id);

        TipoVehiculo tipoVehiculo = tipoVehiculoRepository.findById(dto.getTipoVehiculoId())
                .orElseThrow(() -> new RuntimeException("Tipo de vehículo no encontrado con ID: " + dto.getTipoVehiculoId()));

        vehiculo.setTipoVehiculo(tipoVehiculo);
        vehiculo.setMarca(dto.getMarca());
        vehiculo.setColor(dto.getColor());
        vehiculo.setModelo(dto.getModelo());

        if (dto.getClienteId() != null) {
            Cliente cliente = clienteRepository.findById(dto.getClienteId())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + dto.getClienteId()));
            vehiculo.setCliente(cliente);
        } else {
            vehiculo.setCliente(null);
        }

        return vehiculoRepository.save(vehiculo);
    }

    public void eliminar(Long id) {
        Vehiculo vehiculo = obtenerPorId(id);
        vehiculoRepository.delete(vehiculo);
    }

    /**
     * Asocia un vehículo a un cliente
     */
    public Vehiculo asociarCliente(Long vehiculoId, Long clienteId) {
        Vehiculo vehiculo = obtenerPorId(vehiculoId);
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + clienteId));
        vehiculo.setCliente(cliente);
        return vehiculoRepository.save(vehiculo);
    }
}

