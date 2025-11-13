package com.example.demo.repository;

import com.example.demo.model.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ViajeRepository extends JpaRepository<Viaje, Integer> {

    /**
     * Obtiene una lista de Modelos de Viaje cuyo atributo 'estado' es TRUE.
     * Estos son los modelos/configuraciones disponibles para la venta.
     *  Lista de modelos de viaje activos.
     */
    List<Viaje> findByEstadoTrue();
    
    
}
