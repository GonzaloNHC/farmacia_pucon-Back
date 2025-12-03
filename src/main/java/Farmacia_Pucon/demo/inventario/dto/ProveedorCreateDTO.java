package Farmacia_Pucon.demo.inventario.dto;

public class ProveedorCreateDTO {
   private String rut;
    private String nombre;
    private String contacto;
    private String correo;

    public ProveedorCreateDTO() {}

    // getters y setters...
    public String getRut(){ return rut; }
    public void setRut(String rut){ this.rut = rut; }
    public String getNombre(){ return nombre; }
    public void setNombre(String nombre){ this.nombre = nombre; }
    public String getContacto(){ return contacto; }
    public void setContacto(String contacto){ this.contacto = contacto; }
    public String getCorreo(){ return correo; }
    public void setCorreo(String correo){ this.correo = correo; }
}