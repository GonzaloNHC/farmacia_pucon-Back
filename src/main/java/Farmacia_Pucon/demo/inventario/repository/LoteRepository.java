package Farmacia_Pucon.demo.inventario.repository;

import Farmacia_Pucon.demo.inventario.entity.Lote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoteRepository extends JpaRepository<Lote, Long> {
}
