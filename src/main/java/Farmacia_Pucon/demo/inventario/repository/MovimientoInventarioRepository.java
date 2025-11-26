package Farmacia_Pucon.demo.inventario.repository;

import Farmacia_Pucon.demo.inventario.domain.MovimientoInventario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventario, Long> {
}
