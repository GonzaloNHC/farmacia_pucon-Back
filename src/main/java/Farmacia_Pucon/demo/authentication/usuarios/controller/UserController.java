package Farmacia_Pucon.demo.authentication.usuarios.controller;

import Farmacia_Pucon.demo.authentication.usuarios.domain.User;
import Farmacia_Pucon.demo.authentication.usuarios.dto.UserRequestDTO;
import Farmacia_Pucon.demo.authentication.usuarios.dto.UserResponseDTO;
import Farmacia_Pucon.demo.authentication.usuarios.service.UserService;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /** CREATE USER */
    @PostMapping
    public UserResponseDTO create(@RequestBody UserRequestDTO dto) {

        User user = new User();
        user.setNombreCompleto(dto.getNombreCompleto());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());

        User saved = userService.createUser(user, dto.getRolesIds());

        return toDTO(saved);
    }

    /** GET ALL */
    @GetMapping
    public List<UserResponseDTO> getAll() {
        return userService.getAllUsers()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /** GET ONE BY USERNAME */
    @GetMapping("/username/{username}")
    public UserResponseDTO getByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return toDTO(user);
    }

    /** UPDATE USER */
    @PutMapping("/{id}")
    public UserResponseDTO updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDTO dto
    ) {
        User user = new User();
        user.setNombreCompleto(dto.getNombreCompleto());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());

        User updated = userService.updateUser(id, user, dto.getRolesIds());

        return toDTO(updated);
    }

    /** DELETE USER */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    /** Convertir entidad a DTO limpio */
    private UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getNombreCompleto(),
                user.getUsername(),
                user.getRoles().stream()
                        .map(r -> r.getNombre())
                        .collect(Collectors.toSet())
        );
    }
}