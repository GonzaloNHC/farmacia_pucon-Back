package Farmacia_Pucon.demo.inventario.service.impl;

import Farmacia_Pucon.demo.inventario.domain.Medicamento;
import Farmacia_Pucon.demo.inventario.dto.MedicamentoRequestDTO;
import Farmacia_Pucon.demo.inventario.dto.MedicamentoResponseDTO;
import Farmacia_Pucon.demo.inventario.repository.MedicamentoRepository;
import Farmacia_Pucon.demo.inventario.service.MedicamentoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicamentoServiceImpl implements MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;

    public MedicamentoServiceImpl(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    @Override
    public MedicamentoResponseDTO crear(MedicamentoRequestDTO request) {

        if (request.getNombreComercial() == null || request.getNombreGenerico() == null) {
            throw new RuntimeException("El nombre comercial y el nombre genérico son obligatorios");
        }

        if (medicamentoRepository.existsByNombreComercialIgnoreCase(request.getNombreComercial())) {
            throw new RuntimeException("Ya existe un medicamento con ese nombre comercial");
        }

        Medicamento entity = new Medicamento();
        entity.setNombreComercial(request.getNombreComercial());
        entity.setNombreGenerico(request.getNombreGenerico());
        entity.setPresentacion(request.getPresentacion());
        entity.setDosificacion(request.getDosificacion());
        entity.setActivo(request.getActivo() != null ? request.getActivo() : true);

        Medicamento guardado = medicamentoRepository.save(entity);

        return toResponse(guardado);
    }

    @Override
    public List<MedicamentoResponseDTO> listar() {
        return medicamentoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MedicamentoResponseDTO obtener(Long id) {
        Medicamento entity = medicamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));

        return toResponse(entity);
    }

    @Override
    public MedicamentoResponseDTO actualizar(Long id, MedicamentoRequestDTO request) {

        Medicamento entity = medicamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));

        if (request.getNombreComercial() != null) {
            entity.setNombreComercial(request.getNombreComercial());
        }
        if (request.getNombreGenerico() != null) {
            entity.setNombreGenerico(request.getNombreGenerico());
        }
        if (request.getPresentacion() != null) {
            entity.setPresentacion(request.getPresentacion());
        }
        if (request.getDosificacion() != null) {
            entity.setDosificacion(request.getDosificacion());
        }
        if (request.getActivo() != null) {
            entity.setActivo(request.getActivo());
        }

        Medicamento actualizado = medicamentoRepository.save(entity);

        return toResponse(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        // Soft delete: marcar como inactivo
        Medicamento entity = medicamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));

        entity.setActivo(false);
        medicamentoRepository.save(entity);
    }

    @Override
    public List<MedicamentoResponseDTO> buscarPorTexto(String texto) {

        if (texto == null || texto.trim().isEmpty()) {
            return listar();
        }

        List<Medicamento> resultado = new ArrayList<>();
        resultado.addAll(medicamentoRepository.findByNombreComercialContainingIgnoreCase(texto));
        resultado.addAll(medicamentoRepository.findByNombreGenericoContainingIgnoreCase(texto));

        // Evitar duplicados si aparece en ambos
        return resultado.stream()
                .distinct()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private MedicamentoResponseDTO toResponse(Medicamento entity) {
        return new MedicamentoResponseDTO(
                entity.getId(),
                entity.getNombreComercial(),
                entity.getNombreGenerico(),
                entity.getPresentacion(),
                entity.getDosificacion(),
                entity.getActivo()
        );
    }
}
