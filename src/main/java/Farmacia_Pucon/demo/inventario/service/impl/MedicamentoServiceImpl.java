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

    public MedicamentoServiceImpl(MedicamentoRepository medicamentoRepository,
            CodigoBarrasService codigoBarrasService) {
        this.medicamentoRepository = medicamentoRepository;
        this.codigoBarrasService = codigoBarrasService;
    }

    @Override
    public MedicamentoResponseDTO crear(MedicamentoRequestDTO request) {
        Medicamento entity = new Medicamento();
        entity.setNombreComercial(request.getNombreComercial());
        entity.setNombreGenerico(request.getNombreGenerico());
        entity.setPresentacion(request.getPresentacion());
        entity.setDosificacion(request.getDosificacion());

        // Activo por defecto si viene null
        if (request.getActivo() != null) {
            entity.setActivo(request.getActivo());
        } else {
            entity.setActivo(true);
        }

        // ⚠️ IMPORTANTE: aquí NO usamos request.getCodigoBarras()
        // Generamos el código de barras encriptado a partir de los datos del medicamento
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
        entity.setPresentacion(request.getPresentacion());
        entity.setDosificacion(request.getDosificacion());

        CodigoBarras codigo = codigoBarrasService.generarParaMedicamento(entity);
        entity.setCodigoBarras(codigo);

        if (request.getActivo() != null) {
            entity.setActivo(request.getActivo());
        }

        if (request.getCodigoBarras() != null && !request.getCodigoBarras().isBlank()) {
            entity.setCodigoBarras(new CodigoBarras(request.getCodigoBarras()));
        }

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
        if (!medicamentoRepository.existsById(id)) {
            throw new IllegalArgumentException("Medicamento no encontrado");
        }
        medicamentoRepository.deleteById(id);
    }

    @Override
    public List<MedicamentoResponseDTO> buscarPorTexto(String texto) {
        List<Medicamento> porNombreComercial = medicamentoRepository
                .findByNombreComercialContainingIgnoreCase(texto);
        List<Medicamento> porNombreGenerico = medicamentoRepository
                .findByNombreGenericoContainingIgnoreCase(texto);

        // unimos sin duplicar
        return porNombreComercial.stream()
                .distinct()
                .collect(Collectors.toList())
                .stream()
                .collect(Collectors.toList())
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MedicamentoResponseDTO buscarPorCodigoBarras(String codigoBarras) {
        Medicamento entity = medicamentoRepository
                .findByCodigoBarras_Valor(codigoBarras)
                .orElseThrow(() -> new RuntimeException("No se encontró medicamento con ese código de barras"));
        return toResponse(entity);
    }

    @Override
    public String decodificarCodigoBarras(String codigoBarras) {
        // Esto te devuelve el texto original (nombreComercial|nombreGenerico|presentacion|dosificacion)
        return codigoBarrasService.desencriptar(codigoBarras);
    }

    private MedicamentoResponseDTO toResponse(Medicamento entity) {
        String codigo = entity.getCodigoBarras() != null
                ? entity.getCodigoBarras().getValor()
                : null;

        return new MedicamentoResponseDTO(
                entity.getId(),
                entity.getNombreComercial(),
                entity.getNombreGenerico(),
                entity.getPresentacion(),
                entity.getDosificacion(),
                entity.getActivo(),
                codigo
        );
    }
}
