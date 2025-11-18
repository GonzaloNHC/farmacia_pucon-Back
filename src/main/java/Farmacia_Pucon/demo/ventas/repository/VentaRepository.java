package Farmacia_Pucon.demo.ventas.repository;

import Farmacia_Pucon.demo.ventas.domain.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Long> {
}
