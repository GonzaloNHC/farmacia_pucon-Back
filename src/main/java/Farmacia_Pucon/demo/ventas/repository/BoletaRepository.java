package Farmacia_Pucon.demo.ventas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Farmacia_Pucon.demo.ventas.domain.Boleta;

public interface BoletaRepository extends JpaRepository<Boleta, Long> {

    Optional<Boleta> findByFolio(String folio);

    Optional<Boleta> findByVentaId(Long ventaId);
}
