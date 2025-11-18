package Farmacia_Pucon.demo.authentication.usuarios.dto;

import java.util.List;

public class UserRequestDTO {

    private String nombreCompleto;
    private String username;
    private String password;
    private List<Long> rolesIds;

    public String getNombreCompleto() { return nombreCompleto; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public List<Long> getRolesIds() { return rolesIds; }
}
