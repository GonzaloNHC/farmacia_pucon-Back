package Farmacia_Pucon.demo.inventario.repository;

import Farmacia_Pucon.demo.inventario.domain.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {

    boolean existsByNombreComercialIgnoreCase(String nombreComercial);

    List<Medicamento> findByNombreComercialContainingIgnoreCase(String texto);

    List<Medicamento> findByNombreGenericoContainingIgnoreCase(String texto);

    Optional<Medicamento> findByCodigoBarras_Valor(String valor);

}
