package Farmacia_Pucon.demo.authentication.role.dto;

import java.util.List;

public class RoleRequestDTO {

    private String nombre;
    private String descripcion;
    private List<Long> permissions;

    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public List<Long> getPermissions() { return permissions; }
}