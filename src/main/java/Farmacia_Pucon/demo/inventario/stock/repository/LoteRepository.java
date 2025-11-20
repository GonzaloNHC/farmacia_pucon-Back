package Farmacia_Pucon.demo.inventario.stock.repository;

import Farmacia_Pucon.demo.inventario.stock.domain.Lote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoteRepository extends JpaRepository<Lote, Long> {

    List<Lote> findByMedicamentoId(Long medicamentoId);

    List<Lote> findByActivoTrue();

    List<Lote> findByMedicamentoIdAndActivoTrue(Long medicamentoId);
}
