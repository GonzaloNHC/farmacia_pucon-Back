package Farmacia_Pucon.demo.authentication.role.service;

import Farmacia_Pucon.demo.authentication.role.domain.Role;
import java.util.List;

public interface RoleService {

    Role createRole(Role role, List<Long> permisosIds);

    List<Role> getRoles();

    Role updateRole(Long id, Role roleData, List<Long> permisosIds);
}