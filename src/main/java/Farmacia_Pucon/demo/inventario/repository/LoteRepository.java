package Farmacia_Pucon.demo.inventario.repository;

import Farmacia_Pucon.demo.inventario.domain.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LoteRepository extends JpaRepository<Lote, Long> {

    List<Lote> findByMedicamentoIdOrderByFechaVencimientoAsc(Long medicamentoId);

    List<Lote> findByActivoTrue();

    List<Lote> findByMedicamentoIdAndActivoTrue(Long medicamentoId);

    @Query("SELECT COALESCE(SUM(l.cantidadDisponible), 0) FROM Lote l WHERE l.medicamento.id = :medicamentoId AND l.activo = true")
    Integer obtenerStockTotal(Long medicamentoId);
}
