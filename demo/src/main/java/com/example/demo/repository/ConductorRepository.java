package com.example.demo.repository;


import com.example.demo.model.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ConductorRepository extends JpaRepository<Conductor, Integer> {

    /**
     * Obtiene una lista de Modelos de Conductor cuyo atributo 'estado' es TRUE.
     * Estos son los modelos/configuraciones disponibles para la venta.
     *  Lista de modelos de conductor activos.
     */
    List<Conductor> findByEstadoTrue();
    
    
}
