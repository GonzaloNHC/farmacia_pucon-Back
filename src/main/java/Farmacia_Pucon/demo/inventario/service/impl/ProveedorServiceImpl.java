package Farmacia_Pucon.demo.inventario.service.impl;

import java.time.LocalDateTime;

import javax.print.PrintService;

import org.springframework.stereotype.Service;

import Farmacia_Pucon.demo.inventario.domain.Proveedor;
import Farmacia_Pucon.demo.inventario.dto.ProveedorCreateDTO;
import Farmacia_Pucon.demo.inventario.dto.ProveedorDTO;
import Farmacia_Pucon.demo.inventario.exception.BadRequestException;
import Farmacia_Pucon.demo.inventario.exception.ResourceNotFoundException;
import Farmacia_Pucon.demo.inventario.mapper.ProveedorMapper;
import Farmacia_Pucon.demo.inventario.repository.ProveedorRepository;
import Farmacia_Pucon.demo.inventario.service.ProveedorService;
import Farmacia_Pucon.demo.inventario.utils.RUTValidator;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class ProveedorServiceImpl implements ProveedorService  {

    private final ProveedorRepository repo;

    public ProveedorServiceImpl(ProveedorRepository repo) {
        this.repo = repo;
    }

    @Override
    public ProveedorDTO crear(ProveedorCreateDTO dto) {
        validarDto(dto, true);

        if (repo.existsByRut(normalizarRut(dto.getRut()))) {
            throw new BadRequestException("RUT ya registrado");
        }

        Proveedor p = ProveedorMapper.toEntity(dto);
        p.setRut(normalizarRut(dto.getRut()));
        p.setCreatedAt(LocalDateTime.now());
        p = repo.save(p);
        return ProveedorMapper.toDto(p);
    }

    @Override
    public ProveedorDTO actualizar(Long id, ProveedorCreateDTO dto) {
        validarDto(dto, false);
        Proveedor p = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado"));
        // Si actualizan RUT, validar duplicado
        if (StringUtils.hasText(dto.getRut()) && !normalizarRut(dto.getRut()).equals(p.getRut())) {
            if (repo.existsByRut(normalizarRut(dto.getRut()))) {
                throw new BadRequestException("RUT ya registrado a otro proveedor");
            }
            p.setRut(normalizarRut(dto.getRut()));
        }
        ProveedorMapper.updateEntityFromDto(dto, p);
        p.setUpdatedAt(LocalDateTime.now());
        p = repo.save(p);
        return ProveedorMapper.toDto(p);
    }

    @Override
    public ProveedorDTO obtenerPorId(Long id) {
        Proveedor p = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado"));
        return ProveedorMapper.toDto(p);
    }

    @Override
    public List<ProveedorDTO> listar(Boolean activo) {
        List<Proveedor> list;
        if (activo == null) {
            list = repo.findAll();
        } else {
            list = repo.findAll().stream().filter(p -> p.getActivo().equals(activo)).collect(Collectors.toList());
        }
        return list.stream().map(ProveedorMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void softDelete(Long id) {
        Proveedor p = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado"));
        p.setActivo(false);
        p.setUpdatedAt(LocalDateTime.now());
        repo.save(p);
    }

    @Override
    public ProveedorDTO activar(Long id) {
        Proveedor p = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado"));
        p.setActivo(true);
        p.setUpdatedAt(LocalDateTime.now());
        return ProveedorMapper.toDto(repo.save(p));
    }

    @Override
    public ProveedorDTO desactivar(Long id) {
        Proveedor p = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado"));
        p.setActivo(false);
        p.setUpdatedAt(LocalDateTime.now());
        return ProveedorMapper.toDto(repo.save(p));
    }

    // ---------- Validaciones ----------
    private void validarDto(ProveedorCreateDTO dto, boolean esCreacion) {
        if (dto == null) throw new BadRequestException("Cuerpo de la petición inválido");
        if (!StringUtils.hasText(dto.getNombre())) throw new BadRequestException("Nombre es obligatorio");
        if (esCreacion && !StringUtils.hasText(dto.getRut())) throw new BadRequestException("RUT es obligatorio");
        if (StringUtils.hasText(dto.getRut()) && !RUTValidator.validarRut(dto.getRut())) {
            throw new BadRequestException("RUT inválido");
        }
        if (StringUtils.hasText(dto.getCorreo()) && !esEmailValido(dto.getCorreo())) {
            throw new BadRequestException("Correo inválido");
        }
    }

    private boolean esEmailValido(String correo) {
        return correo != null && correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    private String normalizarRut(String rut) {
        if (rut == null) return null;
        return rut.replace(".", "").replace(" ", "").toUpperCase();
    }
}
