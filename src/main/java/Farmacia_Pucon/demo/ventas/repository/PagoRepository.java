package Farmacia_Pucon.demo.ventas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Farmacia_Pucon.demo.ventas.domain.Pago;

public interface PagoRepository extends JpaRepository<Pago, Long> {
}