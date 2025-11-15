package com.example.demo.service;
import com.example.demo.model.Vehiculo;
import com.example.demo.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

// Indica a Spring que esta clase es un componente de servicio
@Service
public class VehiculoService {

    // Inyección de dependencias: permite usar los métodos del Vehiculo
    @Autowired
    private VehiculoRepository vehiculoRepository;

    // Métodos CRUD (5 métodos requeridos) 

    // 1. CREAR / GUARDAR (Create)
    /**
     * Guarda un nuevo Vehiculo o actualiza uno existente.
     *  El objeto Vehiculo a persistir.
     *  El objeto Vehiculo guardado/actualizado.
     */
    public Vehiculo guardarVehiculo(Vehiculo vehiculo) {
        // La lógica de negocio podría ir aquí (ej: validar email antes de guardar)
        return vehiculoRepository.save(vehiculo);
    }
    
    // 2. "LEER TODOS' (Read All) - Filtrado por Borrado Lógico
    /**
     * Obtiene todos los vehiculo cuyo estado es TRUE (activos).
     * Usa el Query Method definido en el Repository.
     *  Lista de vehiculos activos.
     */
    public List<Vehiculo> obtenerTodosVehiculoActivos() {
        return vehiculoRepository.findByEstadoTrue();
    }
    
    // 3. LEER POR ID (Read By ID)
    /**
     * Obtiene un vehiculo por su ID, independientemente de su estado (activo o inactivo).
     *  El ID del vehiculo a buscar.
     *  Un objeto Optional que puede contener el vehiculo.
     */
    public Optional<Vehiculo> obtenerVehiculoPorId(Integer vehiculoId) {
        // Usamos findById que devuelve un Optional para manejar la posible ausencia del vehiculo.
        return vehiculoRepository.findById(vehiculoId);
    }
    
    // 4. ACTUALIZAR (Update)
    /**
     * Actualiza la información de un vehiculo existente.
     * id El ID del vehiculo a actualizar.
     * detallesVehiculo Los nuevos datos del Vehiculo.
     *  El vehiculo actualizado o null si no se encontró.
     */
    public Vehiculo actualizarVehiculo(Integer vehiculoId, Vehiculo detallesVehiculo) {
        // 1. Busca el Vehiculo existente
        return vehiculoRepository.findById(vehiculoId).map(vehiculoExistente -> {
            // 2. Actualiza los campos (se asume que el ID ya está validado)
            vehiculoExistente.setEstado(detallesVehiculo.getEstado());
            vehiculoExistente.setTipoVehiculo(detallesVehiculo.getTipoVehiculo());
            vehiculoExistente.setModelo(detallesVehiculo.getModelo());
             vehiculoExistente.setColor(detallesVehiculo.getColor());
            vehiculoExistente.setPatente(detallesVehiculo.getPatente());
           
            
            // Nota: Podrías optar por no actualizar el estado aquí, o dejar que la lógica de soft-delete lo maneje.
            // Para simplicidad, la actualización de estado solo se hace en eliminarVehiculoLogico.
            
            // 3. Guarda la entidad actualizada
            return vehiculoRepository.save(vehiculoExistente);
        }).orElse(null); // Devuelve null si no encuentra el vehiculo
    }

    // 5. ELIMINAR (Delete) - Borrado Lógico
    /**
     * Realiza un borrado lógico, cambiando el atributo 'estado' a FALSE.
     *  El ID del vehiculo a desactivar.
     *  true si la eliminación lógica fue exitosa, false si el Vehiculo no fue encontrado.
     */
    public boolean eliminarVehiculoLogico(Integer vehiculoId) {
        Optional<Vehiculo> vehiculoEncontrado = vehiculoRepository.findById(vehiculoId);    
        if (vehiculoEncontrado.isPresent()) {
            Vehiculo vehiculo = vehiculoEncontrado.get();
            vehiculo.setEstado(false); // 🔑 Lógica clave: Borrado Lógico
            vehiculoRepository.save(vehiculo); // Persiste el cambio de estado
            return true;
        }
        return false; // Vehiculo no encontrado para eliminar
    }
}

