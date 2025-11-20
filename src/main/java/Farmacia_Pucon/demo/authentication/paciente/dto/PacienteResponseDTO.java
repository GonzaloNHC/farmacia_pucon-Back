package Farmacia_Pucon.demo.authentication.paciente.dto;

public class PacienteResponseDTO {

    private Long id;
    private String rut;
    private String nombreCompleto;
    private String telefono;
    private String direccion;
    private String email;

    public PacienteResponseDTO() {}

    public PacienteResponseDTO(Long id, String rut, String nombreCompleto,
                               String telefono, String direccion, String email) {
        this.id = id;
        this.rut = rut;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.direccion = direccion;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getRut() {
        return rut;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getEmail() {
        return email;
    }
}
