package com.example.demo.service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

// Indica a Spring que esta clase es un componente de servicio
@Service
public class UsuarioService {

    // Inyección de dependencias: permite usar los métodos del Repository
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Métodos CRUD (5 métodos requeridos) 

    // 1. CREAR / GUARDAR (Create)
    /**
     * Guarda un nuevo Usuario o actualiza uno existente.
     */
    public Usuario guardarUsuario(Usuario Usuario) {
        // La lógica de negocio podría ir aquí (ej: validar email antes de guardar)
        return usuarioRepository.save(Usuario);
    }
    
    // 2. "LEER TODOS' (Read All) - Filtrado por Borrado Lógico
    /**
     * Obtiene todos los usuarios cuyo estado es TRUE (activos).
     * Usa el Query Method definido en el Repository.
     *  Lista de usuarios activos.
     */
    public List<Usuario> obtenerTodosUsuariosActivos() {
        return usuarioRepository.findByEstadoTrue();
    }
    
    // 3. LEER POR ID (Read By ID)
    /**
     * Obtiene un cliente por su ID, independientemente de su estado (activo o inactivo).
     *  El ID del cliente a buscar.
     *  Un objeto Optional que puede contener el Cliente.
     */
    public Optional<Usuario> obtenerUsuarioPorId(Integer usuarioId) {
        // Usamos findById que devuelve un Optional para manejar la posible ausencia del cliente.
        return usuarioRepository.findById(usuarioId);
    }
    
    // 4. ACTUALIZAR (Update)
    /**
     * Actualiza la información de un usuario existente.
     * id El ID del usuario a actualizar.
     * detalles Usuario Los nuevos datos del usuario.
     *  El usuario actualizado o null si no se encontró.
     */
    public Usuario actualizarUsuario(Integer usuarioId, Usuario detallesUsuario) {
        // 1. Busca el usuario existente
        return usuarioRepository.findById(usuarioId).map(usuarioExistente -> {
            // 2. Actualiza los campos (se asume que el ID ya está validado)
            usuarioExistente.setNombre(detallesUsuario.getNombre());
            usuarioExistente.setApellido(detallesUsuario.getApellido());
            usuarioExistente.setcorreoElectronico(detallesUsuario.getcorreoElectronico());
            usuarioExistente.setTelefono(detallesUsuario.getTelefono());
            
            //Optar por no actualizar el estado aquí, o dejar que la lógica de soft-delete lo maneje.
            // Para simplicidad, la actualización de estado solo se hace en eliminarClienteLogico.
            
            // 3. Guarda la entidad actualizada
            return usuarioRepository.save(usuarioExistente);
        }).orElse(null); // Devuelve null si no encuentra el cliente
    }

    // 5. ELIMINAR (Delete) - Borrado Lógico
    /**
     * Realiza un borrado lógico, cambiando el atributo 'estado' a FALSE.
     *  El ID del cliente a desactivar.
     *  true si la eliminación lógica fue exitosa, false si el usuario no fue encontrado.
     */
    public boolean eliminarUsuarioLogico(Integer usuarioId) {
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findById(usuarioId);    
        if (usuarioEncontrado.isPresent()) {
            Usuario usuario = usuarioEncontrado.get();
            usuario.setEstado(false); // 🔑 Lógica clave: Borrado Lógico
            usuarioRepository.save(usuario); // Persiste el cambio de estado
            return true;
        }
        return false; // usuario no encontrado para eliminar
    }
}
