package com.example.demo.service;

import com.example.demo.model.Viaje;
import com.example.demo.repository.ViajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

// Indica a Spring que esta clase es un componente de servicio
@Service
public class ViajeService {

    // Inyección de dependencias: permite usar los métodos del Viaje
    @Autowired
    private ViajeRepository viajeRepository;

    // Métodos CRUD (5 métodos requeridos) 

    // 1. CREAR / GUARDAR (Create)
    /**
     * Guarda un nuevo Viaje o actualiza uno existente.
     *  El objeto Viaje a persistir.
     *  El objeto Viaje guardado/actualizado.
     */
    public Viaje guardarViaje(Viaje viaje) {
        // La lógica de negocio podría ir aquí (ej: validar email antes de guardar)
        return viajeRepository.save(viaje);
    }
    
    // 2. "LEER TODOS' (Read All) - Filtrado por Borrado Lógico
    /**
     * Obtiene todos los viaje cuyo estado es TRUE (activos).
     * Usa el Query Method definido en el Repository.
     *  Lista de viaje activos.
     */
    public List<Viaje> obtenerTodosViajeActivos() {
        return viajeRepository.findByEstadoTrue();
    }
    
    // 3. LEER POR ID (Read By ID)
    /**
     * Obtiene un viaje por su ID, independientemente de su estado (activo o inactivo).
     *  El ID del viaje a buscar.
     *  Un objeto Optional que puede contener el viaje.
     */
    public Optional<Viaje> obtenerViajePorId(Integer viajeId) {
        // Usamos findById que devuelve un Optional para manejar la posible ausencia del viaje.
        return viajeRepository.findById(viajeId);
    }
    
    // 4. ACTUALIZAR (Update)
    /**
     * Actualiza la información de un viaje existente.
     * id El ID del viaje a actualizar.
     * detallesViaje Los nuevos datos del Viaje.
     *  El viaje actualizado o null si no se encontró.
     */
    public Viaje actualizarViaje(Integer viajeId, Viaje detallesViaje) {
        // 1. Busca el Viaje existente
        return viajeRepository.findById(viajeId).map(viajeExistente -> {
            // 2. Actualiza los campos (se asume que el ID ya está validado)
            viajeExistente.setFecha(detallesViaje.getFecha());
            viajeExistente.setTipoViaje(detallesViaje.getTipoViaje());
            viajeExistente.setCosto(detallesViaje.getCosto());
           
           
            
            // Nota: Podrías optar por no actualizar el estado aquí, o dejar que la lógica de soft-delete lo maneje.
            // Para simplicidad, la actualización de estado solo se hace en eliminarViajeLogico.
            
            // 3. Guarda la entidad actualizada
            return viajeRepository.save(viajeExistente);
        }).orElse(null); // Devuelve null si no encuentra el viaje
    }

    // 5. ELIMINAR (Delete) - Borrado Lógico
    /**
     * Realiza un borrado lógico, cambiando el atributo 'estado' a FALSE.
     *  El ID del vehiculo a desactivar.
     *  true si la eliminación lógica fue exitosa, false si el Viaje no fue encontrado.
     */
    public boolean eliminarViajeloLogico(Integer viajeId) {
        Optional<Viaje> viajeEncontrado = viajeRepository.findById(viajeId);    
        if (viajeEncontrado.isPresent()) {
            Viaje viaje = viajeEncontrado.get();
            viaje.setEstado(false); // 🔑 Lógica clave: Borrado Lógico
            viajeRepository.save(viaje); // Persiste el cambio de estado
            return true;
        }
        return false; // Viaje no encontrado para eliminar
    }
}



