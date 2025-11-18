package Farmacia_Pucon.demo.authentication.role.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "permissions")
public class Permission {

    public Permission() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String codigo;

    // RELACIÓN INVERSA (opcional)
    // @ManyToMany(mappedBy = "permissions")
    // private Set<Role> roles;

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
}
