package Farmacia_Pucon.demo.inventario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Farmacia_Pucon.demo.inventario.domain.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    boolean existsByRut(String rut);
    Optional<Proveedor> findByRut(String rut);
}