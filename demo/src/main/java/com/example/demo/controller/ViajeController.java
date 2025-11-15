package com.example.demo.controller;

import com.example.demo.model.Viaje;
import com.example.demo.service.ViajeService;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller; // CAMBIADO a @Controller
import org.springframework.ui.Model; // IMPORTADO para pasar datos a la vista
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
//import java.util.Optional; // Necesario para la búsqueda por ID

// Define que esta clase es un controlador MVC tradicional

@Controller

public class ViajeController {

    private final ViajeService viajeService;

    // Inyección de Dependencias por Constructor (Java 17 style)
    public ViajeController(ViajeService viajeService) {
        this.viajeService = viajeService;
    }

    // --- MÉTODOS MVC ---

    // 1. Mostrar la lista de viaje en verdadero (READ ALL - Vista Principal)
    // GET /viaje/
    @GetMapping("/listarViaje")
    public String listarViajeActivos(Model model) {
        // Obtenemos solo los viaje activos
        List<Viaje> viaje = viajeService.obtenerTodosViajeActivos();

        // Agregamos la lista al objeto Model para que la vista pueda acceder a ella
        model.addAttribute("viaje", viaje);

        // Retorna el nombre de la plantilla HTML a renderizar (ej: Thymeleaf o JSP)
        return "listaViaje";
    }

    // 2. Mostrar el formulario para registrar un nuevo viaje
    // GET /viaje/nuevo
    @GetMapping("/nuevoViaje")
    public String mostrarFormularioRegistroViaje(Model model) {
        // Agregamos un objeto Viaje vacío para que el formulario pueda llenarlo
        model.addAttribute("viaje", new Viaje());
        return "formViaje"; // Vista HTML del formulario
    }

    // 3. Guardar nuevo viaje (CREATE)
    // POST /viaje/guardar
    @PostMapping("/guardarVije")
    public String guardarViaje(@ModelAttribute Viaje viaje) {
        // El servicio guarda el objeto enviado desde el formulario
        viajeService.guardarViaje(viaje);

        // Redirige al usuario a la lista principal después de guardar
        return "redirect:/listarViaje";
    }

    // 4. VER DETALLE DEL VIAJE (READ By ID) - MÁS CONCISO
    // GET /detalleViaje/{id}
    @GetMapping("/detalleViaje/{id}")
    public String verDetalleViaje(@PathVariable("id") Integer id, Model model) {

        // Uso de orElseThrow():
        // 1. Llama al servicio, que devuelve un Optional<Viaje>.
        // 2. Si el Optional está vacío, orElseThrow lanza la excepción
        // ResponseStatusException.
        // 3. Spring captura esta excepción y devuelve automáticamente un código de
        // estado HTTP 404.

        Viaje viaje = viajeService.obtenerViajePorId(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Viaje no encontrado con ID: " + id));
        // Si el viaje fue encontrado, el código continúa aquí.
        model.addAttribute("viaje", viaje);

        // Retorna el nombre de la plantilla HTML de detalle
        return "detalleViaje";
    }

    // 5. ELIMINAR VIAJE (DELETE - Borrado Lógico)
    // GET /eliminarViaje/{id}
    @GetMapping("/eliminarViaje/{id}")
    public String eliminarViajeLogico(@PathVariable("id") Integer id) {

        viajeService.eliminarViajeLogico(id);

        // Redirige al usuario a la lista principal después de la operación
        return "redirect:/listarViaje";
    }

    // EDITAR VEHICULO (UPDATE) // GET /editarVehiculo/{id}

    // 6. MOSTRAR FORMULARIO PARA EDITAR (UPDATE - GET)
    // GET /editarViaje/{id}
    @GetMapping("/editarViaje/{id}")
    public String mostrarFormularioEdicion(@PathVariable("id") Integer id, Model model) {
        // 1. Obtener el viaje por ID, lanzando 404 si no existe
    Viaje viaje = viajeService.obtenerViajePorId(id)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Viaje no encontrado para editar con ID: " + id));
    
    // 2. Agregar el viaje encontrado al modelo
    model.addAttribute("viaje", viaje );
        // 2. Agregar el viaje encontrado al modelo
        model.addAttribute("viaje", viaje);

        // 3. Reutilizar la vista del formulario (que ya está preparada para edición)
        return "formViaje";
    }

    // 7. PROCESAR ACTUALIZACIÓN (UPDATE - POST)
    // POST /actualizarViaje/{id}
    @PostMapping("/actualizarViaje/{id}")
    public String actualizarViaje(@PathVariable("id") Integer id, @ModelAttribute Viaje viajeActualizado) {
        // 1. Establecer el ID en el objeto recibido del formulario
        // Esto es vital ya que el ModelAttribute lo crea, pero necesitamos el ID para
        // el Service.
        viajeActualizado.setViajeId(id);

        // 2. Llamar al servicio de actualización
        Viaje viajeResultado = viajeService.actualizarViaje(id, viajeActualizado);

        // 3. Manejo de error (si no se encontró el viaje original en el servicio)
        

        // 4. Redirigir a la lista de viaje o a la vista de detalle
        return "redirect:/listarViaje"; // O "redirect:/detalleViaje/" + id;
    }

}
