package com.example.demo.controller;


import com.example.demo.model.Conductor;
import com.example.demo.service.ConductorService;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller; // CAMBIADO a @Controller
import org.springframework.ui.Model; // IMPORTADO para pasar datos a la vista
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
//import java.util.Optional; // Necesario para la búsqueda por ID

// Define que esta clase es un controlador MVC tradicional

@Controller

public class ConductorController {

    private final ConductorService conductorService;

    // Inyección de Dependencias por Constructor (Java 17 style)
    public ConductorController(ConductorService conductorService) {
        this.conductorService = conductorService;
    }

    // --- MÉTODOS MVC ---

    // 1. Mostrar la lista de conductor en verdadero (READ ALL - Vista Principal)
    // GET /conductor/
    @GetMapping("/listarconductor")
    public String listarconductorActivos(Model model) {
        // Obtenemos solo los vehiculo activos
        List<Conductor> conductores = conductorService.obtenerTodosConductorActivos();

        // Agregamos la lista al objeto Model para que la vista pueda acceder a ella
        model.addAttribute("conductores", conductores);

        // Retorna el nombre de la plantilla HTML a renderizar (ej: Thymeleaf o JSP)
        return "listaConductor";
    }

    // 2. Mostrar el formulario para registrar un nuevo conductor
    // GET /conductor/nuevo
    @GetMapping("/nuevoconductor")
    public String mostrarFormularioRegistroConductor(Model model) {
        // Agregamos un objeto conductor vacío para que el formulario pueda llenarlo
        model.addAttribute("conductor", new Conductor());
        return "formularioConductor"; // Vista HTML del formulario
    }

    // 3. Guardar nuevo conductor (CREATE)
    // POST /conductor/guardar
    @PostMapping("/guardarConductor")
    public String guardarconductor(@ModelAttribute Conductor conductor) {
        // El servicio guarda el objeto enviado desde el formulario
        conductorService.guardarConductor(conductor);

        // Redirige al usuario a la lista principal después de guardar
        return "redirect:/listarconductor";
    }

    // 4. VER DETALLE DEL CONDUCTOR (READ By ID) - MÁS CONCISO
    // GET /detalleConductor/{id}
    @GetMapping("/detalleConductor/{id}")
    public String verDetalleconductor(@PathVariable("id") Integer id, Model model) {

        // Uso de orElseThrow():
        // 1. Llama al servicio, que devuelve un Optional<Conductor>.
        // 2. Si el Optional está vacío, orElseThrow lanza la excepción
        // ResponseStatusException.
        // 3. Spring captura esta excepción y devuelve automáticamente un código de
        // estado HTTP 404.

        Conductor conductor = conductorService.obtenerConductorPorId(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conductor no encontrado con ID: " + id));
        // Si el conductor fue encontrado, el código continúa aquí.
        model.addAttribute("conductor", conductor);

        // Retorna el nombre de la plantilla HTML de detalle
        return "detalleConductor";
    }

    // 5. ELIMINAR CONDUCTOR (DELETE - Borrado Lógico)
    // GET /eliminarConductor/{id}
    @GetMapping("/eliminarConductor/{id}")
    public String eliminarConductorLogico(@PathVariable("id") Integer id) {

        conductorService.eliminarConductorLogico(id);

        // Redirige al usuario a la lista principal después de la operación
        return "redirect:/listarConductor";
    }

    // EDITAR CONDUCTOR (UPDATE) // GET /editarConductor/{id}


    // 6. MOSTRAR FORMULARIO PARA EDITAR (UPDATE - GET)
    // GET /editarConductor/{id}
    @GetMapping("/editarConductor/{id}")
    public String mostrarFormularioEdicion(@PathVariable("id") Integer id, Model model) {
        // 1. Obtener el conductor por ID, lanzando 404 si no existe
    Conductor conductor = conductorService.obtenerConductorPorId(id)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conductor no encontrado para editar con ID: " + id));
    
    // 2. Agregar el conductor encontrado al modelo
    model.addAttribute("conductor", conductor);
        // 2. Agregar el conductor encontrado al modelo
        model.addAttribute("conductor", conductor);

        // 3. Reutilizar la vista del formulario (que ya está preparada para edición)
        return "formularioConductor";
    }

    // 7. PROCESAR ACTUALIZACIÓN (UPDATE - POST)
    // POST /actualizarConductor/{id}
    @PostMapping("/actualizarConductor/{id}")
    public String actualizarConductor(@PathVariable("id") Integer id, @ModelAttribute Conductor conductorActualizado) {
        // 1. Establecer el ID en el objeto recibido del formulario
        // Esto es vital ya que el ModelAttribute lo crea, pero necesitamos el ID para
        // el Service.
        conductorActualizado.setConductorId(id);

        // 2. Llamar al servicio de actualización
        Conductor conductorResultado = conductorService.actualizarConductor(id, conductorActualizado);

        // 3. Manejo de error (si no se encontró el conductor original en el servicio)
        

        // 4. Redirigir a la lista de conductor o a la vista de detalle
        return "redirect:/listarConductor"; // O "redirect:/detalleConductor/" + id;
    }

}
