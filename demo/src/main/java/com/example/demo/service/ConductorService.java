package com.example.demo.service;

import com.example.demo.model.Conductor;
import com.example.demo.repository.ConductorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

// Indica a Spring que esta clase es un componente de servicio
@Service
public class ConductorService {

    // Inyección de dependencias: permite usar los métodos del Conductor
    @Autowired
    private ConductorRepository conductorRepository;

    // Métodos CRUD (5 métodos requeridos) 

    // 1. CREAR / GUARDAR (Create)
    /**
     * Guarda un nuevo conductor o actualiza uno existente.
     *  El objeto Conductor a persistir.
     *  El objeto Conductor guardado/actualizado.
     */
    public Conductor guardarConductor(Conductor conductor) {
        // La lógica de negocio podría ir aquí (ej: validar email antes de guardar)
        return conductorRepository.save(conductor);
    }
    
    // 2. "LEER TODOS' (Read All) - Filtrado por Borrado Lógico
    /**
     * Obtiene todos los conductor cuyo estado es TRUE (activos).
     * Usa el Query Method definido en el Repository.
     *  Lista de conductor activos.
     */
    public List<Conductor> obtenerTodosConductorActivos() {
        return conductorRepository.findByEstadoTrue();
    }
    
    // 3. LEER POR ID (Read By ID)
    /**
     * Obtiene un conductor por su ID, independientemente de su estado (activo o inactivo).
     *  El ID del conductor a buscar.
     *  Un objeto Optional que puede contener el conductor.
     */
    public Optional<Conductor> obtenerConductorPorId(Integer conductorId) {
        // Usamos findById que devuelve un Optional para manejar la posible ausencia del conductor.
        return conductorRepository.findById(conductorId);
    }
    
    // 4. ACTUALIZAR (Update)
    /**
     * Actualiza la información de un conductor existente.
     * id El ID del conductor a actualizar.
     * detallesConductor Los nuevos datos del conductor.
     *  El conductor actualizado o null si no se encontró.
     */
    public Conductor actualizarConductor(Integer conductorId, Conductor detallesconductor) {
        // 1. Busca el Vehiculo existente
        return conductorRepository.findById(conductorId).map(conductorExistente -> {
            // 2. Actualiza los campos (se asume que el ID ya está validado)
            conductorExistente.setEstado(detallesConductor.getEstado());
            conductorExistente.setNombre(detallesConductor.getNombre());
            conductorExistente.setApellido(detallesConductor.getApellido());
             conductorExistente.setTelefono(detallesConductor.getTelefono());
           
           
            
            // Nota: Podrías optar por no actualizar el estado aquí, o dejar que la lógica de soft-delete lo maneje.
            // Para simplicidad, la actualización de estado solo se hace en eliminarConductorLogico.
            
            // 3. Guarda la entidad actualizada
            return conductorRepository.save(conductorExistente);
        }).orElse(null); // Devuelve null si no encuentra el conducta
    }

    // 5. ELIMINAR (Delete) - Borrado Lógico
    /**
     * Realiza un borrado lógico, cambiando el atributo 'estado' a FALSE.
     *  El ID del conductor a desactivar.
     *  true si la eliminación lógica fue exitosa, false si el Conductor no fue encontrado.
     */
    public boolean eliminarConductor(Integer vehiculoId) {
        Optional<Conductor> conductorEncontrado = conductorRepository.findById(conductorId);    
        if (conductorEncontrado.isPresent()) {
            Conductor conductor = conductorEncontrado.get();
            conductor.setEstado(false); // 🔑 Lógica clave: Borrado Lógico
            conductorRepository.save(conductor); // Persiste el cambio de estado
            return true;
        }
        return false; // Conductor no encontrado para eliminar
    }
}

