package Farmacia_Pucon.demo.authentication.usuarios.dto;

import java.util.Set;

public class UserResponseDTO {

    private Long id;
    private String nombreCompleto;
    private String username;
    private Set<String> roles;

    public UserResponseDTO(Long id, String nombreCompleto, String username, Set<String> roles) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.username = username;
        this.roles = roles;
    }

    public Long getId() { return id; }
    public String getNombreCompleto() { return nombreCompleto; }
    public String getUsername() { return username; }
    public Set<String> getRoles() { return roles; }
}
