package Farmacia_Pucon.demo.role.service;

import Farmacia_Pucon.demo.role.domain.Role;
import java.util.List;

public interface RoleService {

    Role createRole(Role role, List<Long> permisosIds);

    List<Role> getRoles();

    Role updateRole(Long id, Role roleData, List<Long> permisosIds);
}