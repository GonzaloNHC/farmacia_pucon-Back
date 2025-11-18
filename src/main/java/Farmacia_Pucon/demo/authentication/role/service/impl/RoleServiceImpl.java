package Farmacia_Pucon.demo.authentication.role.service.impl;

import Farmacia_Pucon.demo.authentication.role.domain.Permission;
import Farmacia_Pucon.demo.authentication.role.domain.Role;
import Farmacia_Pucon.demo.authentication.role.repository.PermissionRepository;
import Farmacia_Pucon.demo.authentication.role.repository.RoleRepository;
import Farmacia_Pucon.demo.authentication.role.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleServiceImpl(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Role createRole(Role role, List<Long> permisosIds) {
        Set<Permission> permisos = new HashSet<>(permissionRepository.findAllById(permisosIds));
        role.setPermissions(permisos);
        return roleRepository.save(role);
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role updateRole(Long id, Role roleData, List<Long> permisosIds) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        role.setNombre(roleData.getNombre());
        role.setDescripcion(roleData.getDescripcion());

        Set<Permission> permisos = new HashSet<>(permissionRepository.findAllById(permisosIds));
        role.setPermissions(permisos);

        return roleRepository.save(role);
    }
}
