package Farmacia_Pucon.demo.inventario.ingreso.repository;

import Farmacia_Pucon.demo.inventario.ingreso.domain.DetalleIngreso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleIngresoRepository extends JpaRepository<DetalleIngreso, Long> {
}
