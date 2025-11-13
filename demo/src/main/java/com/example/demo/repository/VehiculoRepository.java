package com.example.demo.repository;

import com.example.demo.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {

    /**
     * Obtiene una lista de Modelos de Vehiculo cuyo atributo 'estado' es TRUE.
     * Estos son los modelos/configuraciones disponibles para la venta.
     *  Lista de modelos de vehiculo activos.
     */
    List<Vehiculo> findByEstadoTrue();
    
    
}


 
