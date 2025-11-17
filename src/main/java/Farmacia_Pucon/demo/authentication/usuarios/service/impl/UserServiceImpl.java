package Farmacia_Pucon.demo.authentication.usuarios.service.impl;

import Farmacia_Pucon.demo.authentication.usuarios.domain.User;
import Farmacia_Pucon.demo.authentication.usuarios.repository.UserRepository;
import Farmacia_Pucon.demo.authentication.usuarios.service.UserService;
import Farmacia_Pucon.demo.authentication.role.domain.Role;
import Farmacia_Pucon.demo.authentication.role.repository.RoleRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User user, List<Long> rolesIds) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<Role> roles = new HashSet<>(roleRepository.findAllById(rolesIds));
        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long id, User userData, List<Long> rolesIds) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setNombreCompleto(userData.getNombreCompleto());
        user.setUsername(userData.getUsername());

        if (userData.getPassword() != null && !userData.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userData.getPassword()));
        }

        Set<Role> roles = new HashSet<>(roleRepository.findAllById(rolesIds));
        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        userRepository.deleteById(id);
    }
}
