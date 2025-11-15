package Farmacia_Pucon.demo.role.repository;

import Farmacia_Pucon.demo.role.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

}