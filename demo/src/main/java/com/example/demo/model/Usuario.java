package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity

public class Usuario {
    
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer usuarioId; // PK

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private String correoElectronico;

    @Column
    private String telefono;

    // Relación 1:N con Viaje un usuario hace muchos viajes
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Viaje> viajes = new ArrayList<>();

    // Atributo de Borrado Lógico
    @Column(nullable = false)
    private boolean estado = true;

    //Constructores,Getters y Setters...

    // Constructor vacío
    public Usuario() {}

    // Constructor con parámetros
    public Usuario(Integer usuarioId, String nombre, String apellido, String correoElectronico, String telefono) {
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
        this.estado = true;
    }

    // Getters y Setters
     public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico (String correoElectronico) { this.correoElectronico = correoElectronico; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public List<Viaje> getViajes() { return viajes; }
    public void setViajes(List<Viaje> viajes) { this.viajes= viajes; }

    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }
}