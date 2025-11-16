package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

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
    private String color;

    @Column
    private String patente;

    // Relación 1:N con Viaje (un vehículo tiene muchos viajes)
    @OneToMany(mappedBy = "vehiculo") // esta mapeando , buscando por vehiculo, vehiculo por vehiculo
    private List<Viaje> viajes = new ArrayList<>();

    // Relación 1:1 con Conductor
    @OneToOne
    @JoinColumn(name = "conductorId")
    private Conductor conductor;

    // Constructor vacío
    public Vehiculo() {}

    // Constructor con parámetros (sin relaciones)
    public Vehiculo(Integer vehiculoId, boolean estado, String tipoVehiculo, String modelo,
                    String color, String patente, List<Viaje> viajes, Conductor conductor) {
        this.vehiculoId = vehiculoId;
        this.estado = estado;
        this.tipoVehiculo = tipoVehiculo;
        this.modelo = modelo;
        this.color = color;
        this.patente = patente;
        this.viajes = viajes;
        this.conductor = conductor;
    }

    // Getters y Setters
    public Integer getVehiculoId() { return vehiculoId; }
    public void setVehiculoId(Integer vehiculoId) { this.vehiculoId = vehiculoId; }

    public Boolean getEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }

    public String getTipoVehiculo() { return tipoVehiculo; }
    public void setTipoVehiculo(String tipoVehiculo) { this.tipoVehiculo = tipoVehiculo; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getPatente() { return patente; }
    public void setPatente(String patente) { this.patente = patente; }

    public List<Viaje> getViajes() { return viajes; }
    public void setViajes(List<Viaje> viajes) { this.viajes = viajes; }

    public Conductor getConductor() { return conductor; }
    public void setConductor(Conductor conductor) { this.conductor = conductor; }
}
