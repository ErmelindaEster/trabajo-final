package com.example.demo.controller;

import com.example.demo.model.Vehiculo;
import com.example.demo.service.VehiculoService;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller; // CAMBIADO a @Controller
import org.springframework.ui.Model; // IMPORTADO para pasar datos a la vista
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
//import java.util.Optional; // Necesario para la búsqueda por ID

// Define que esta clase es un controlador MVC tradicional

@Controller

public class VehiculoController {

    private final VehiculoService vehiculoService;

    // Inyección de Dependencias por Constructor (Java 17 style)
    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    // --- MÉTODOS MVC ---

    // 1. Mostrar la lista de vehiculo en verdadero (READ ALL - Vista Principal)
    // GET /vehiculo/
    @GetMapping("/listarVehiculo")
    public String listarVehiculoActivos(Model model) {
        // Obtenemos solo los vehiculo activos
        List<Vehiculo> vehiculo = vehiculoService.obtenerTodosVehiculoActivos();

        // Agregamos la lista al objeto Model para que la vista pueda acceder a ella
        model.addAttribute("vehiculo", vehiculo);

        // Retorna el nombre de la plantilla HTML a renderizar (ej: Thymeleaf o JSP)
        return "listaVehiculo";
    }

    // 2. Mostrar el formulario para registrar un nuevo vehiculo
    // GET /vehiculo/nuevo
    @GetMapping("/nuevoVehiculo")
    public String mostrarFormularioRegistroVehiculo(Model model) {
        // Agregamos un objeto Vehiculo vacío para que el formulario pueda llenarlo
        model.addAttribute("vehiculo", new Vehiculo());
        return "formVehiculo"; // Vista HTML del formulario
    }

    // 3. Guardar nuevo vehiculo (CREATE)
    // POST /vehiculo/guardar
    @PostMapping("/guardarVehiculo")
    public String guardarVehiculo(@ModelAttribute Vehiculo vehiculo) {
        // El servicio guarda el objeto enviado desde el formulario
        vehiculoService.guardarVehiculo(vehiculo);

        // Redirige al usuario a la lista principal después de guardar
        return "redirect:/listarVehiculo";
    }

    // 4. VER DETALLE DEL VEHICULO (READ By ID) - MÁS CONCISO
    // GET /detalleVehiculo/{id}
    @GetMapping("/detalleVehiculo/{id}")
    public String verDetalleVehiculo(@PathVariable("id") Integer id, Model model) {

        // Uso de orElseThrow():
        // 1. Llama al servicio, que devuelve un Optional<Vehiculo>.
        // 2. Si el Optional está vacío, orElseThrow lanza la excepción
        // ResponseStatusException.
        // 3. Spring captura esta excepción y devuelve automáticamente un código de
        // estado HTTP 404.

        Vehiculo vehiculo = vehiculoService.obtenerVehiculoPorId(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehiculo no encontrado con ID: " + id));
        // Si el vehiculo fue encontrado, el código continúa aquí.
        model.addAttribute("vehiculo", vehiculo);

        // Retorna el nombre de la plantilla HTML de detalle
        return "detalleVehiculo";
    }

    // 5. ELIMINAR VEHICULO (DELETE - Borrado Lógico)
    // GET /eliminarVehiculo/{id}
    @GetMapping("/eliminarVehiculo/{id}")
    public String eliminarVehiculoLogico(@PathVariable("id") Integer id) {

        vehiculoService.eliminarVehiculoLogico(id);

        // Redirige al usuario a la lista principal después de la operación
        return "redirect:/listarClientes";
    }

    // EDITAR VEHICULO (UPDATE) // GET /editarVehiculo/{id}





    // 6. MOSTRAR FORMULARIO PARA EDITAR (UPDATE - GET)
    // GET /editarVehiculo/{id}
    @GetMapping("/editarVehiculo/{id}")
    public String mostrarFormularioEdicion(@PathVariable("id") Integer id, Model model) {
        // 1. Obtener el vehiculo por ID, lanzando 404 si no existe
    Vehiculo vehiculo = vehiculoService.obtenerVehiculoPorId(id)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehiculo no encontrado para editar con ID: " + id));
    
    // 2. Agregar el vehiculo encontrado al modelo
    model.addAttribute("vehiculo", vehiculo);
        // 2. Agregar el vehiculo encontrado al modelo
        model.addAttribute("vehiculo", vehiculo);

        // 3. Reutilizar la vista del formulario (que ya está preparada para edición)
        return "formCliente";
    }

    // 7. PROCESAR ACTUALIZACIÓN (UPDATE - POST)
    // POST /actualizarCliente/{id}
    @PostMapping("/actualizarVehiculo/{id}")
    public String actualizarVehiculo(@PathVariable("id") Integer id, @ModelAttribute Vehiculo vehiculoActualizado) {
        // 1. Establecer el ID en el objeto recibido del formulario
        // Esto es vital ya que el ModelAttribute lo crea, pero necesitamos el ID para
        // el Service.
        vehiculoActualizado.setVehiculoId(id);

        // 2. Llamar al servicio de actualización
        Vehiculo vehiculoResultado = vehiculoService.actualizarVehiculo(id, vehiculoActualizado);

        // 3. Manejo de error (si no se encontró el cliente original en el servicio)
        

        // 4. Redirigir a la lista de vehiculo o a la vista de detalle
        return "redirect:/listarVehiculo"; // O "redirect:/detalleVehiculo/" + id;
    }

}
