
package com.example.demo.model;
//import java.util.ArrayList;
//import java.util.List;


import jakarta.persistence.*;

@Entity
public class Conductor {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer conductorId; // PK

      @Column
    private boolean estado = true;

    @Column
    private String nombre;

    @Column
    private String apellido;
   @Column
    private String telefono; 



    // Relación 1:1 con Vehiculo: un conductor tienen un vehiculo.
    @OneToOne
    @JoinColumn(name = "conductorId") 
    private Conductor conductor;

    


     // Constructor Vacío
    public Conductor() {}

    // Constructor con parámetros (sin relaciones)
    public Conductor(Integer conductorId , boolean estado, String nombre, String apellido,String telefono, String patente) {
        this.conductorId = conductorId;
        this.estado = estado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;  
    }

   // Getter y  setter

   public Integer getConductorId() { return conductorId; }
    public void setConductorId(Integer conductorId) { this.conductorId = conductorId; }
    
    public Boolean getEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }
    
      public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }


}
