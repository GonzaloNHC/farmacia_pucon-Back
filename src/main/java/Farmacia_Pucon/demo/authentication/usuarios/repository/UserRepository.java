package Farmacia_Pucon.demo.authentication.usuarios.repository;

import Farmacia_Pucon.demo.authentication.usuarios.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
