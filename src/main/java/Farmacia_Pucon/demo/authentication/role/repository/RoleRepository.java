package Farmacia_Pucon.demo.authentication.role.repository;
import Farmacia_Pucon.demo.authentication.role.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

}