package com.example.demo.controller;


import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller; // CAMBIADO a @Controller
import org.springframework.ui.Model; // IMPORTADO para pasar datos a la vista
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
public class UsuarioController {
     private final UsuarioService usuarioService;

    // Inyección por constructor
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // --- MÉTODOS MVC ---

    // 1. LISTAR USUARIOS ACTIVOS
    @GetMapping("/listarUsuarios")
    public String listarUsuariosActivos(Model model) {
        List<Usuario> usuarios = usuarioService.obtenerTodosUsuariosActivos();
        model.addAttribute("usuarios", usuarios);
        return "listaUsuario";
    }

    // 2. MOSTRAR FORMULARIO NUEVO USUARIO
    @GetMapping("/nuevoUsuario")
    public String mostrarFormularioRegistroUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "formularioUsuario";
    }

    // 3. GUARDAR NUEVO USUARIO
    
    @PostMapping("/guardarUsuario")
    public String guardarUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.guardarUsuario(usuario);
        return "redirect:/listarUsuarios";
    }

    // 4. DETALLE USUARIO POR ID
    @GetMapping("/detalleUsuario/{id}")
    public String verDetalleUsuario(@PathVariable("id") Integer id, Model model) {

        Usuario usuario = usuarioService.obtenerUsuarioPorId(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                        "Usuario no encontrado con ID: " + id));

        model.addAttribute("usuario", usuario);
        return "detalleUsuario";
    }

    // 5. ELIMINAR USUARIO (BORRADO LÓGICO)
    @GetMapping("/eliminarUsuario/{id}")
    public String eliminarUsuarioLogico(@PathVariable("id") Integer id) {

        usuarioService.eliminarUsuarioLogico(id);

        return "redirect:/listarUsuarios";
    }

    // 6. FORMULARIO PARA EDITAR USUARIO
    @GetMapping("/editarUsuario/{id}")
    public String mostrarFormularioEdicion(@PathVariable("id") Integer id, Model model) {

        Usuario usuario = usuarioService.obtenerUsuarioPorId(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                        "Usuario no encontrado para editar con ID: " + id));

        model.addAttribute("usuario", usuario);
        return "formularioUsuario";
    }

    // 7. PROCESAR ACTUALIZACIÓN
    @PostMapping("/actualizarUsuario/{id}")
    public String actualizarUsuario(@PathVariable("id") Integer id, 
                                    @ModelAttribute Usuario usuarioActualizado) {

        usuarioActualizado.setUsuarioId(id);

        Usuario usuarioResultado = usuarioService.actualizarUsuario(id, usuarioActualizado);

        return "redirect:/listarUsuarios";
    }

}