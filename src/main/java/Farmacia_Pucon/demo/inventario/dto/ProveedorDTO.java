package Farmacia_Pucon.demo.inventario.dto;

public class ProveedorDTO {
    private Long id;
    private String rut;
    private String nombre;
    private String contacto;
    private String correo;
    private Boolean activo;

    public ProveedorDTO() {}

    // getters y setters...
    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }
    public String getRut(){ return rut; }
    public void setRut(String rut){ this.rut = rut; }
    public String getNombre(){ return nombre; }
    public void setNombre(String nombre){ this.nombre = nombre; }
    public String getContacto(){ return contacto; }
    public void setContacto(String contacto){ this.contacto = contacto; }
    public String getCorreo(){ return correo; }
    public void setCorreo(String correo){ this.correo = correo; }
    public Boolean getActivo(){ return activo; }
    public void setActivo(Boolean activo){ this.activo = activo; }
}