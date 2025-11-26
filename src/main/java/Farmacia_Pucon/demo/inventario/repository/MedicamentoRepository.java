package Farmacia_Pucon.demo.inventario.repository;

import Farmacia_Pucon.demo.inventario.domain.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {

    boolean existsByNombreComercialIgnoreCase(String nombreComercial);

    List<Medicamento> findByNombreComercialContainingIgnoreCase(String texto);

    List<Medicamento> findByNombreGenericoContainingIgnoreCase(String texto);

<<<<<<< HEAD
    Optional<Medicamento> findByCodigoBarras_Valor(String valor);

=======
    @Query("""
           SELECT m 
           FROM Medicamento m
           WHERE m.activo = true 
             AND (
                 LOWER(m.nombreComercial) LIKE LOWER(CONCAT('%', :query, '%'))
                 OR LOWER(m.nombreGenerico) LIKE LOWER(CONCAT('%', :query, '%'))
             )
           """)
    List<Medicamento> buscarActivosPorNombre(@Param("query") String query);
>>>>>>> feature/devpeque
}
