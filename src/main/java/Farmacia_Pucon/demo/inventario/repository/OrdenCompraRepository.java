package Farmacia_Pucon.demo.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import Farmacia_Pucon.demo.inventario.domain.OrdenCompra;

public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Long> {
}