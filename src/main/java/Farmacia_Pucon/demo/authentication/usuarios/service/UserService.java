package Farmacia_Pucon.demo.authentication.usuarios.service;

import Farmacia_Pucon.demo.authentication.usuarios.domain.User;
import java.util.List;

public interface UserService {

    User createUser(User user, List<Long> rolesIds);

    User getUserByUsername(String username);  // ← nombre correcto

    List<User> getAllUsers();

    User updateUser(Long id, User userData, List<Long> rolesIds);

    void deleteUser(Long id);
}