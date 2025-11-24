package Farmacia_Pucon.demo.authentication.usuarios.dto;

import java.util.List;

public class UserRequestDTO {

    private String nombreCompleto;
    private String username;
    private String password;
    private List<Long> rolesIds;

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<Long> getRolesIds() { return rolesIds; }
    public void setRolesIds(List<Long> rolesIds) { this.rolesIds = rolesIds; }
}
