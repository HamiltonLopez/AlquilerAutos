package com.alquilerautos.service;

import com.alquilerautos.model.Usuario;
import com.alquilerautos.repository.UsuarioRepository;
import com.alquilerautos.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private Validator validator;

    @InjectMocks
    private UsuarioService usuarioService;

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
    void whenSaveUsuario_thenReturnUsuario() {
        when(validator.validate(any())).thenReturn(new HashSet<>());
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario saved = usuarioService.save(usuario);

        assertNotNull(saved);
        assertEquals("Juan Pérez", saved.getNombre());
        assertEquals("juan@example.com", saved.getCorreo());
        assertEquals("CLIENTE", saved.getRol());
        verify(usuarioRepository).save(any(Usuario.class));
        verify(validator).validate(any());
    }

    @Test
    void whenGetAll_thenReturnList() {
        List<Usuario> usuarios = Arrays.asList(usuario);
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<Usuario> found = usuarioService.getAll();

        assertNotNull(found);
        assertEquals(1, found.size());
        verify(usuarioRepository).findAll();
    }

    @Test
    void whenSearch_thenReturnUsuario() {
        List<Usuario> usuarios = Arrays.asList(usuario);
        when(usuarioRepository.findByNombreContainingIgnoreCaseOrCorreoContainingIgnoreCase(anyString(), anyString()))
            .thenReturn(usuarios);

        List<Usuario> found = usuarioService.search("Juan");

        assertNotNull(found);
        assertEquals(1, found.size());
        assertEquals("Juan Pérez", found.get(0).getNombre());
        verify(usuarioRepository).findByNombreContainingIgnoreCaseOrCorreoContainingIgnoreCase(anyString(), anyString());
    }

    @Test
    void whenUpdateUsuario_thenReturnUpdatedUsuario() {
        when(validator.validate(any())).thenReturn(new HashSet<>());
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre("Juan García");
        nuevoUsuario.setCorreo("juan.garcia@example.com");
        nuevoUsuario.setRol("ADMIN");
        nuevoUsuario.setContrasena("newpassword123");

        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(nuevoUsuario);

        Usuario updated = usuarioService.update(1L, nuevoUsuario);

        assertNotNull(updated);
        assertEquals("Juan García", updated.getNombre());
        assertEquals("juan.garcia@example.com", updated.getCorreo());
        assertEquals("ADMIN", updated.getRol());
        verify(usuarioRepository).findById(anyLong());
        verify(usuarioRepository).save(any(Usuario.class));
        verify(validator).validate(any());
    }

    @Test
    void whenUpdateUsuarioNotFound_thenThrowException() {
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            usuarioService.update(1L, new Usuario());
        });

        verify(usuarioRepository).findById(anyLong());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void whenDeleteUsuario_thenVerifyRepositoryCall() {
        when(usuarioRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(usuarioRepository).deleteById(anyLong());

        usuarioService.delete(1L);

        verify(usuarioRepository).existsById(1L);
        verify(usuarioRepository).deleteById(1L);
    }

    @Test
    void whenDeleteUsuarioNotFound_thenThrowException() {
        when(usuarioRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            usuarioService.delete(1L);
        });

        verify(usuarioRepository).existsById(1L);
        verify(usuarioRepository, never()).deleteById(anyLong());
    }
} 