package org.example.service;

import org.example.dto.ConsultaCostoResponseDTO;
import org.example.dto.IngresoVehiculoDTO;
import org.example.model.RegistroParqueo;
import org.example.model.Tarifa;
import org.example.model.Vehiculo;
import org.example.model.enums.EstadoMensualidad;
import org.example.model.enums.EstadoRegistro;
import org.example.model.enums.TipoTarifa;
import org.example.repository.MensualidadRepository;
import org.example.repository.RegistroParqueoRepository;
import org.example.repository.TarifaRepository;
import org.example.repository.VehiculoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegistroParqueoService {

    private final RegistroParqueoRepository registroParqueoRepository;
    private final VehiculoRepository vehiculoRepository;
    private final TarifaRepository tarifaRepository;
    private final MensualidadRepository mensualidadRepository;

    public RegistroParqueoService(RegistroParqueoRepository registroParqueoRepository,
                                  VehiculoRepository vehiculoRepository,
                                  TarifaRepository tarifaRepository,
                                  MensualidadRepository mensualidadRepository) {
        this.registroParqueoRepository = registroParqueoRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.tarifaRepository = tarifaRepository;
        this.mensualidadRepository = mensualidadRepository;
    }

    public List<RegistroParqueo> listarTodos() {
        return registroParqueoRepository.findAll();
    }

    public List<RegistroParqueo> listarActivos() {
        return registroParqueoRepository.findByEstado(EstadoRegistro.ACTIVO);
    }

    public RegistroParqueo obtenerPorId(Long id) {
        return registroParqueoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro de parqueo no encontrado con ID: " + id));
    }

    /**
     * Ingresa un vehículo al parqueadero.
     * Verifica que no esté ya ingresado (registro activo).
     * Verifica que el vehículo exista en el sistema.
     */
    public RegistroParqueo ingresarVehiculo(IngresoVehiculoDTO dto) {
        String placa = dto.getPlaca().toUpperCase();

        // Verificar que el vehículo exista
        Vehiculo vehiculo = vehiculoRepository.findByPlaca(placa)
                .orElseThrow(() -> new RuntimeException("Vehículo con placa " + placa
                        + " no está registrado en el sistema. Primero debe registrar el vehículo."));

        // Verificar que no tenga un registro activo (ya esté dentro)
        if (registroParqueoRepository.existsByVehiculoIdAndEstado(vehiculo.getId(), EstadoRegistro.ACTIVO)) {
            throw new RuntimeException("El vehículo con placa " + placa + " ya se encuentra dentro del parqueadero.");
        }

        RegistroParqueo registro = new RegistroParqueo();
        registro.setVehiculo(vehiculo);
        registro.setObservaciones(dto.getObservaciones());

        return registroParqueoRepository.save(registro);
    }

    /**
     * Gestiona la salida de un vehículo.
     * Calcula el costo según la tarifa por hora y el tiempo transcurrido.
     * Si tiene mensualidad activa, el costo es 0.
     */
    public RegistroParqueo gestionarSalida(String placa) {
        final String placaUpper = placa.toUpperCase();

        RegistroParqueo registro = registroParqueoRepository
                .findByVehiculoPlacaAndEstado(placaUpper, EstadoRegistro.ACTIVO)
                .orElseThrow(() -> new RuntimeException("No se encontró un registro activo para el vehículo con placa: " + placaUpper));

        registro.setFechaSalida(LocalDateTime.now());
        registro.setEstado(EstadoRegistro.FINALIZADO);

        // Verificar si tiene mensualidad activa
        boolean tieneMensualidad = mensualidadRepository
                .findByVehiculoPlacaAndEstadoAndFechaFinGreaterThanEqual(placaUpper, EstadoMensualidad.ACTIVA, LocalDate.now())
                .isPresent();

        if (tieneMensualidad) {
            registro.setTotalCobrado(BigDecimal.ZERO);
        } else {
            BigDecimal costo = calcularCosto(registro);
            registro.setTotalCobrado(costo);
        }

        return registroParqueoRepository.save(registro);
    }

    /**
     * Consulta tiempo y costo acumulado de un vehículo por su placa.
     */
    public ConsultaCostoResponseDTO consultarCostoPorPlaca(String placa) {
        final String placaUpper = placa.toUpperCase();

        RegistroParqueo registro = registroParqueoRepository
                .findByVehiculoPlacaAndEstado(placaUpper, EstadoRegistro.ACTIVO)
                .orElseThrow(() -> new RuntimeException("No se encontró un registro activo para el vehículo con placa: " + placaUpper));

        return construirConsultaCosto(registro);
    }

    /**
     * Consulta los registros activos de los vehículos de un cliente.
     */
    public List<ConsultaCostoResponseDTO> consultarCostoPorCliente(Long clienteId) {
        List<Vehiculo> vehiculos = vehiculoRepository.findByClienteId(clienteId);
        if (vehiculos.isEmpty()) {
            throw new RuntimeException("El cliente no tiene vehículos registrados.");
        }

        return vehiculos.stream()
                .map(v -> registroParqueoRepository.findByVehiculoPlacaAndEstado(v.getPlaca(), EstadoRegistro.ACTIVO))
                .filter(java.util.Optional::isPresent)
                .map(java.util.Optional::get)
                .map(this::construirConsultaCosto)
                .toList();
    }

    // ---- Métodos privados de apoyo ----

    private ConsultaCostoResponseDTO construirConsultaCosto(RegistroParqueo registro) {
        Vehiculo vehiculo = registro.getVehiculo();
        Duration duracion = Duration.between(registro.getFechaIngreso(), LocalDateTime.now());
        long minutos = duracion.toMinutes();
        long horas = duracion.toHours();
        long mins = minutos % 60;

        boolean tieneMensualidad = mensualidadRepository
                .findByVehiculoPlacaAndEstadoAndFechaFinGreaterThanEqual(
                        vehiculo.getPlaca(), EstadoMensualidad.ACTIVA, LocalDate.now())
                .isPresent();

        BigDecimal costo = BigDecimal.ZERO;
        BigDecimal tarifaHoraVal = BigDecimal.ZERO;

        if (!tieneMensualidad) {
            costo = calcularCosto(registro);
            Tarifa tarifaHora = tarifaRepository
                    .findByTipoTarifaAndTipoVehiculoIdAndActivaTrue(TipoTarifa.HORA, vehiculo.getTipoVehiculo().getId())
                    .orElse(null);
            if (tarifaHora != null) {
                tarifaHoraVal = tarifaHora.getMonto();
            }
        }

        ConsultaCostoResponseDTO response = new ConsultaCostoResponseDTO();
        response.setPlaca(vehiculo.getPlaca());
        response.setTipoVehiculo(vehiculo.getTipoVehiculo().getNombre());
        response.setFechaIngreso(registro.getFechaIngreso());
        response.setMinutosTranscurridos(minutos);
        response.setTiempoTranscurrido(horas + "h " + mins + "min");
        response.setCostoAcumulado(costo);
        response.setTarifaHora(tarifaHoraVal);
        response.setTieneMensualidad(tieneMensualidad);

        if (vehiculo.getCliente() != null) {
            response.setNombreCliente(vehiculo.getCliente().getNombre() + " " + vehiculo.getCliente().getApellido());
        }

        return response;
    }

    private BigDecimal calcularCosto(RegistroParqueo registro) {
        Vehiculo vehiculo = registro.getVehiculo();
        LocalDateTime salida = registro.getFechaSalida() != null ? registro.getFechaSalida() : LocalDateTime.now();
        Duration duracion = Duration.between(registro.getFechaIngreso(), salida);
        long minutos = duracion.toMinutes();

        // Buscar tarifa por hora
        Tarifa tarifaHora = tarifaRepository
                .findByTipoTarifaAndTipoVehiculoIdAndActivaTrue(TipoTarifa.HORA, vehiculo.getTipoVehiculo().getId())
                .orElse(null);

        if (tarifaHora == null) {
            return BigDecimal.ZERO;
        }

        // Cobrar hora completa por fracción (mínimo 1 hora)
        long horasCobradas = (minutos <= 0) ? 1 : (long) Math.ceil((double) minutos / 60);
        return tarifaHora.getMonto().multiply(BigDecimal.valueOf(horasCobradas)).setScale(2, RoundingMode.HALF_UP);
    }
}

