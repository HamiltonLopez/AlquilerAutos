package com.alquilerautos.controller;

import com.alquilerautos.model.Usuario;
import com.alquilerautos.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan Pérez");
        usuario.setCorreo("juan@example.com");
        usuario.setRol("CLIENTE");
        usuario.setContrasena("password123");
    }

    @Test
    void whenRegistrarUsuario_thenReturnUsuario() throws Exception {
        when(usuarioService.save(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"))
                .andExpect(jsonPath("$.correo").value("juan@example.com"))
                .andExpect(jsonPath("$.rol").value("CLIENTE"));
    }

    @Test
    void whenListarUsuarios_thenReturnList() throws Exception {
        List<Usuario> usuarios = Arrays.asList(usuario);
        when(usuarioService.getAll()).thenReturn(usuarios);

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan Pérez"))
                .andExpect(jsonPath("$[0].correo").value("juan@example.com"))
                .andExpect(jsonPath("$[0].rol").value("CLIENTE"));
    }

    @Test
    void whenBuscarUsuario_thenReturnUsuario() throws Exception {
        List<Usuario> usuarios = Arrays.asList(usuario);
        when(usuarioService.search(anyString())).thenReturn(usuarios);

        mockMvc.perform(get("/api/usuarios/buscar")
                .param("valor", "Juan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan Pérez"))
                .andExpect(jsonPath("$[0].correo").value("juan@example.com"));
    }

    @Test
    void whenEditarUsuario_thenReturnUpdatedUsuario() throws Exception {
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setId(1L);
        usuarioActualizado.setNombre("Juan García");
        usuarioActualizado.setCorreo("juan.garcia@example.com");
        usuarioActualizado.setRol("ADMIN");
        usuarioActualizado.setContrasena("newpassword123");

        when(usuarioService.update(anyLong(), any(Usuario.class))).thenReturn(usuarioActualizado);

        mockMvc.perform(put("/api/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioActualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan García"))
                .andExpect(jsonPath("$.correo").value("juan.garcia@example.com"))
                .andExpect(jsonPath("$.rol").value("ADMIN"));
    }

    @Test
    void whenEliminarUsuario_thenReturn200() throws Exception {
        doNothing().when(usuarioService).delete(anyLong());

        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isOk());
    }
} 