package Farmacia_Pucon.demo.ventas.repository;

import Farmacia_Pucon.demo.ventas.domain.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
}
