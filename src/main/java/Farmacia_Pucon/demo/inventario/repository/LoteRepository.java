package Farmacia_Pucon.demo.inventario.repository;

import Farmacia_Pucon.demo.inventario.domain.Lote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface LoteRepository extends JpaRepository<Lote, Long> {

    List<Lote> findByMedicamentoIdOrderByFechaVencimientoAsc(Long medicamentoId);

    List<Lote> findByActivoTrue();

    List<Lote> findByMedicamentoIdAndActivoTrue(Long medicamentoId);
}
