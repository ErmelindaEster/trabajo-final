package com.example.demo.model;
//import java.util.ArrayList;
//import java.util.List;

import jakarta.persistence.*;

@Entity
public class Vehiculo {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vehiculoId; // PK

      @Column
    private boolean estado = true;

    @Column
    private String tipoVehiculo;

    @Column
    private String modelo;

    @Column
    private String patente;

    // Relación 1:N con Viaje: un vehiculo hace muchos Viajes.
    @OneToMany
    @JoinColumn(name = "viajeId") 
    private Viaje viaje;

    //  Relación 1:1 con conductor: Un Vehiculo tiene un Conductor (Modelo).
    @OneToOne 
    @JoinColumn(name = "conductorId") 
    private Conductor conductor;


     // Constructor Vacío
    public Vehiculo() {}

    // Constructor con parámetros (sin relaciones)
    public Vehiculo(Integer vehiculoId , boolean estado, String tipoVehículo, String modelo, String patente, Viaje viaje, Conductor conductor) {
        this.vehiculoId = vehiculoId;
        this.estado = estado;
        this.tipoVehiculo = tipoVehículo;
        this.modelo = modelo;
        this.patente = patente;
        this.viaje= viaje; 
        this.conductor= conductor; 
    }

   // Getter y  setter

   public Integer getVehiculoId() { return vehiculoId; }
    public void setVehiculoId(Integer vehiculoId) { this.vehiculoId = vehiculoId; }
    
    public Boolean getEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }
    
      public String getTipoVehiculo() { return tipoVehiculo; }
    public void setTipoVehiculo(String tipoVehiculo) { this.tipoVehiculo = tipoVehiculo; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getPatente() { return patente; }
    public void setPatente(String patente) { this.patente = patente; }
    
    public Viaje getViaje() { return viaje; }
    public void setViaje(Viaje  viaje) { this.viaje = viaje; }

    public Conductor getConductor() { return conductor; }
    public void setConductor(Conductor conductor) { this.conductor = conductor; }
    
}
