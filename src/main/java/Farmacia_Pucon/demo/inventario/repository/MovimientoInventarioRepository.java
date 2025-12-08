package Farmacia_Pucon.demo.inventario.repository;

import Farmacia_Pucon.demo.inventario.domain.MovimientoInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventario, Long> {

    // Buscar todos los movimientos de un lote específico
    List<MovimientoInventario> findByLoteId(Long loteId);

    // Buscar movimientos por tipo: INGRESO, SALIDA, DEVOLUCION
    List<MovimientoInventario> findByTipo(String tipo);

    List<MovimientoInventario> findAllByOrderByFechaHoraDesc();

    /*List<MovimientoInventario> findByLote_Medicamento_Id(Long medicamentoId);*/

    List<MovimientoInventario> findByMedicamento_Id(Long medicamentoId);
}
