package Farmacia_Pucon.demo.inventario.service.impl;

import Farmacia_Pucon.demo.inventario.domain.Medicamento;
import Farmacia_Pucon.demo.inventario.dto.MedicamentoRequestDTO;
import Farmacia_Pucon.demo.inventario.dto.MedicamentoResponseDTO;
import Farmacia_Pucon.demo.inventario.repository.MedicamentoRepository;
import Farmacia_Pucon.demo.inventario.service.MedicamentoService;
import org.springframework.stereotype.Service;

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

        if (medicamentoRepository.existsByNombreComercialIgnoreCase(request.getNombreComercial())) {
            throw new IllegalArgumentException("Ya existe un medicamento con ese nombre comercial");
        }

        Medicamento entity = new Medicamento();
        entity.setNombreComercial(request.getNombreComercial());
        entity.setNombreGenerico(request.getNombreGenerico());
        entity.setPresentacion(request.getPresentacion());
        entity.setDosificacion(request.getDosificacion());
        entity.setActivo(request.getActivo() == null ? true : request.getActivo());

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
        if (request.getActivo() != null) {
            entity.setActivo(request.getActivo());
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
