package Farmacia_Pucon.demo.inventario.repository;

import Farmacia_Pucon.demo.inventario.domain.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {

    // Validación para evitar duplicados
    boolean existsByNombreComercialIgnoreCase(String nombreComercial);

    // Búsquedas básicas
    List<Medicamento> findByNombreComercialContainingIgnoreCase(String texto);

    List<Medicamento> findByNombreGenericoContainingIgnoreCase(String texto);

    // Código de barras
    Optional<Medicamento> findByCodigoBarras_Valor(String valor);

    // ===============================
    // NUEVAS BÚSQUEDAS POR ATRIBUTO
    // ===============================

    List<Medicamento> findByCategoriaIgnoreCase(String categoria);

    List<Medicamento> findByTipoVentaIgnoreCase(String tipoVenta);

    List<Medicamento> findByLaboratorioIgnoreCase(String laboratorio);

    List<Medicamento> findByFormaFarmaceuticaIgnoreCase(String formaFarmaceutica);

    // ===============================
    // BÚSQUEDA GENERAL (nombre o genérico)
    // ===============================
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
}
