package com.unerp.domain.usuario;

import com.unerp.domain.usuario.estado.ActivoState;
import com.unerp.domain.usuario.estado.EstadoUsuario;
import com.unerp.domain.usuario.estado.InactivoState;
import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    @Column(unique = true)
    private String email;

    @Column(name = "password_hash")
    private String passwordHash;

    @Transient
    private EstadoUsuario estado;

    @Column(name = "Estado")
    private String estadoString;

    @Column(name = "rol_id")
    private Integer rolId;

    public Usuario(
            Integer id,
            String nombre,
            String email,
            String passwordHash,
            EstadoUsuario estado,
            Integer rolId
    ) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.passwordHash = passwordHash;
        this.estado = estado;
        this.estadoString = estado.getEstado();
        this.rolId = rolId;
    }

    public Usuario() {
    }

    @PostLoad
    private void cargarEstado() {
        if("activo".equalsIgnoreCase(estadoString)) {
            this.estado = new ActivoState();
        } else {
            this.estado = new InactivoState();
        }
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public EstadoUsuario getEstado() {
        return estado;
    }

    public Integer getRolId() {
        return rolId;
    }

    public void activar() {
            this.estado = this.estado.activar();
    }

    public void desactivar() {
        this.estado.desactivar();
    }
}
