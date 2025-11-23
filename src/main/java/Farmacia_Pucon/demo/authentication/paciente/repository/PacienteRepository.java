package Farmacia_Pucon.demo.authentication.paciente.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Farmacia_Pucon.demo.authentication.paciente.domain.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Optional<Paciente> findByRut(String rut);

    boolean existsByRut(String rut);
}
