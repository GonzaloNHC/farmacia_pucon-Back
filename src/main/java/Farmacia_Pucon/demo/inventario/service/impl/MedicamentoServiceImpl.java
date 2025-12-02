package Farmacia_Pucon.demo.inventario.service.impl;

import Farmacia_Pucon.demo.inventario.domain.Medicamento;
import Farmacia_Pucon.demo.inventario.dto.MedicamentoRequestDTO;
import Farmacia_Pucon.demo.inventario.dto.MedicamentoResponseDTO;
import Farmacia_Pucon.demo.inventario.repository.MedicamentoRepository;
import Farmacia_Pucon.demo.inventario.service.MedicamentoService;
import org.springframework.stereotype.Service;
import Farmacia_Pucon.demo.common.domain.CodigoBarras;
import Farmacia_Pucon.demo.common.service.CodigoBarrasService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicamentoServiceImpl implements MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;
    private final CodigoBarrasService codigoBarrasService;

    public MedicamentoServiceImpl(
            MedicamentoRepository medicamentoRepository,
            CodigoBarrasService codigoBarrasService
    ) {
        this.medicamentoRepository = medicamentoRepository;
        this.codigoBarrasService = codigoBarrasService;
    }

    @Override
public MedicamentoResponseDTO crear(MedicamentoRequestDTO request) {

    if (medicamentoRepository.existsByNombreComercialIgnoreCase(request.getNombreComercial())) {
        throw new IllegalArgumentException("Ya existe un medicamento con ese nombre comercial");
    }

    Medicamento entity = new Medicamento();
    entity.setNombreComercial(request.getNombreComercial());
    entity.setNombreGenerico(request.getNombreGenerico());

    
    entity.setPresentacion(request.getPresentacion());
    entity.setDosificacion(request.getDosificacion());

    entity.setCategoria(request.getCategoria());
    entity.setTipoVenta(request.getTipoVenta());
    entity.setLaboratorio(request.getLaboratorio());
    entity.setFormaFarmaceutica(request.getFormaFarmaceutica());

    entity.setActivo(request.getActivo() != null ? request.getActivo() : true);

    // Código de barras generado automáticamente
    CodigoBarras codigo = codigoBarrasService.generarParaMedicamento(entity);
    entity.setCodigoBarras(codigo);

    Medicamento guardado = medicamentoRepository.save(entity);
    return toResponse(guardado);
}

    @Override
    public MedicamentoResponseDTO actualizar(Long id, MedicamentoRequestDTO request) {
        Medicamento entity = medicamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Medicamento no encontrado"));

        entity.setNombreComercial(request.getNombreComercial());
        entity.setNombreGenerico(request.getNombreGenerico());
        entity.setCategoria(request.getCategoria());
        entity.setTipoVenta(request.getTipoVenta());
        entity.setLaboratorio(request.getLaboratorio());
        entity.setFormaFarmaceutica(request.getFormaFarmaceutica());

        if (request.getActivo() != null) {
            entity.setActivo(request.getActivo());
        }

        // Regenerar código de barras
        CodigoBarras codigo = codigoBarrasService.generarParaMedicamento(entity);
        entity.setCodigoBarras(codigo);

        Medicamento actualizado = medicamentoRepository.save(entity);
        return toResponse(actualizado);
    }

    @Override
    public MedicamentoResponseDTO obtenerPorId(Long id) {
        Medicamento entity = medicamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Medicamento no encontrado"));
        return toResponse(entity);
    }

    @Override
    public List<MedicamentoResponseDTO> listar() {
        return medicamentoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        Medicamento entity = medicamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Medicamento no encontrado"));

        entity.setActivo(false); // soft-delete
        medicamentoRepository.save(entity);
    }

    @Override
    public List<MedicamentoResponseDTO> buscarPorTexto(String texto) {
        return medicamentoRepository.buscarActivosPorNombre(texto)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MedicamentoResponseDTO buscarPorCodigoBarras(String codigoBarras) {
        Medicamento entity = medicamentoRepository.findByCodigoBarras_Valor(codigoBarras)
                .orElseThrow(() -> new RuntimeException("No se encontró medicamento con ese código de barras"));
        return toResponse(entity);
    }

    @Override
    public String decodificarCodigoBarras(String codigoBarras) {
        return codigoBarrasService.desencriptar(codigoBarras);
    }

    @Override
    public List<MedicamentoResponseDTO> buscarPorCategoria(String categoria) {
        return medicamentoRepository.findByCategoriaIgnoreCase(categoria)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicamentoResponseDTO> buscarPorTipoVenta(String tipoVenta) {
        return medicamentoRepository.findByTipoVentaIgnoreCase(tipoVenta)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicamentoResponseDTO> buscarPorLaboratorio(String laboratorio) {
        return medicamentoRepository.findByLaboratorioIgnoreCase(laboratorio)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private MedicamentoResponseDTO toResponse(Medicamento entity) {
        String codigo = entity.getCodigoBarras() != null ? entity.getCodigoBarras().getValor() : null;

        return new MedicamentoResponseDTO(
            entity.getId(),
            entity.getNombreComercial(),
            entity.getNombreGenerico(),
            entity.getPresentacion(),
            entity.getDosificacion(),
            entity.getCategoria(),
            entity.getTipoVenta(),
            entity.getLaboratorio(),
            entity.getFormaFarmaceutica(),
            entity.getActivo(),
            codigo
    );
}
    @Override
    public List<MedicamentoResponseDTO> buscarPorFormaFarmaceutica(String formaFarmaceutica) {
    return medicamentoRepository.findByFormaFarmaceuticaIgnoreCase(formaFarmaceutica)
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
}
}
