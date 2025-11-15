package com.example.demo.model;
import java.time.LocalDate;
//import java.util.ArrayList; 
// import java.util.List; 

import jakarta.persistence.*; 

@Entity
public class Viaje {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer viajeId; // PK

   @Column
    private boolean estado = true;
    
    @Column 
    private String tipoViaje;

      @Column
    private double costo; 

     
     @Column
     private LocalDate fecha;  

    // Relación N:1 con Usuario: Muchas Viajes son hechos por un Usuarios.
    @ManyToOne
    @JoinColumn(name = "usuarioId") 
    private Usuario usuario;

    //  Relación N:1 con Vehiculo: Muchas Viajes son del mismo Vehiculo (Modelo).
    @ManyToOne 
    @JoinColumn(name = "vehiculoId") 
    private Vehiculo vehiculo;


     // constructor vacio
     public Viaje (){}


      //Contructor parametros (sin realciones)

      public Viaje(Integer viajeId, boolean estado, String tipoViaje, double costo, LocalDate fecha, Usuario usuario, Vehiculo vehiculo){

        this.viajeId = viajeId;
        this.estado= estado;
        this.tipoViaje= tipoViaje;
        this.costo= costo;
        this.fecha= fecha;
        this.usuario= usuario;
        this.vehiculo= vehiculo;
      }


       // Getter y Setter
    public Integer getViajeId() { return viajeId; }
    public void setViajeId(Integer viajeId) { this.viajeId = viajeId; }

    public boolean getEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }

    public String getTipoViaje() {return tipoViaje;}
    public void setTipoViaje(String tipoViaje){this.tipoViaje = tipoViaje;}

    public double getCosto(){ return costo;}
    public void setCosto(double costo) {this.costo = costo;}

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha;} 

    public Vehiculo getVehiculo() { return vehiculo; }
    public void setVehiculo(Vehiculo vehiculo) { this.vehiculo = vehiculo; }

    public Usuario getUsuario(){return usuario;}
    public void setUsuario(Usuario usuario){this.usuario = usuario;}



    
}
