package Farmacia_Pucon.demo.inventario.repository;

import Farmacia_Pucon.demo.inventario.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
