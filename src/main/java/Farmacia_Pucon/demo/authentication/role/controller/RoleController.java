package Farmacia_Pucon.demo.authentication.role.controller;

import Farmacia_Pucon.demo.authentication.role.domain.Role;
import Farmacia_Pucon.demo.authentication.role.dto.RoleRequestDTO;
import Farmacia_Pucon.demo.authentication.role.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin("*")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public Role createRole(@RequestBody RoleRequestDTO body) {

        Role role = new Role();
        role.setNombre(body.getNombre());
        role.setDescripcion(body.getDescripcion());

        return roleService.createRole(role, body.getPermissions());
    }

    @GetMapping
    public List<Role> getRoles() {
        return roleService.getRoles();
    }

    @PutMapping("/{id}")
    public Role updateRole(
            @PathVariable Long id,
            @RequestBody RoleRequestDTO body
    ) {
        Role role = new Role();
        role.setNombre(body.getNombre());
        role.setDescripcion(body.getDescripcion());

        return roleService.updateRole(id, role, body.getPermissions());
    }
}
