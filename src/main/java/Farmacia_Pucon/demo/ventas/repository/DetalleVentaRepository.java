package Farmacia_Pucon.demo.ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Farmacia_Pucon.demo.ventas.domain.DetalleVenta;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
}
