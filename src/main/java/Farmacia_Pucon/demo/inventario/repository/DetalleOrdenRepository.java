package Farmacia_Pucon.demo.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import Farmacia_Pucon.demo.inventario.domain.DetalleOrden;

public interface DetalleOrdenRepository extends JpaRepository<DetalleOrden, Long> {
}
