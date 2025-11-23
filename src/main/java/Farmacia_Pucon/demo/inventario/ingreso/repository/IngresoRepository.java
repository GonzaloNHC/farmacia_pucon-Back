package Farmacia_Pucon.demo.inventario.ingreso.repository;

import Farmacia_Pucon.demo.inventario.ingreso.domain.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngresoRepository extends JpaRepository<Ingreso, Long> {
}
