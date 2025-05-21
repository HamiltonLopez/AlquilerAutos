package com.alquilerautos.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void testConstructorAndGetters() {
        Usuario usuario = new Usuario(1L, "Juan Pérez", "juan@example.com", "CLIENTE", "password123");
        
        assertEquals(1L, usuario.getId());
        assertEquals("Juan Pérez", usuario.getNombre());
        assertEquals("juan@example.com", usuario.getCorreo());
        assertEquals("CLIENTE", usuario.getRol());
        assertEquals("password123", usuario.getContrasena());
    }

    @Test
    void testSetters() {
        Usuario usuario = new Usuario();
        
        usuario.setId(1L);
        usuario.setNombre("Juan Pérez");
        usuario.setCorreo("juan@example.com");
        usuario.setRol("CLIENTE");
        usuario.setContrasena("password123");

        assertEquals(1L, usuario.getId());
        assertEquals("Juan Pérez", usuario.getNombre());
        assertEquals("juan@example.com", usuario.getCorreo());
        assertEquals("CLIENTE", usuario.getRol());
        assertEquals("password123", usuario.getContrasena());
    }

    @Test
    void testEqualsAndHashCode() {
        Usuario usuario1 = new Usuario(1L, "Juan Pérez", "juan@example.com", "CLIENTE", "password123");
        Usuario usuario2 = new Usuario(1L, "Juan Pérez", "juan@example.com", "CLIENTE", "password123");
        Usuario usuario3 = new Usuario(2L, "María López", "maria@example.com", "ADMIN", "password456");

        assertEquals(usuario1, usuario2);
        assertEquals(usuario1.hashCode(), usuario2.hashCode());
        assertNotEquals(usuario1, usuario3);
        assertNotEquals(usuario1.hashCode(), usuario3.hashCode());
    }

    @Test
    void testToString() {
        Usuario usuario = new Usuario(1L, "Juan Pérez", "juan@example.com", "CLIENTE", "password123");
        String toString = usuario.toString();

        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("nombre=Juan Pérez"));
        assertTrue(toString.contains("correo=juan@example.com"));
        assertTrue(toString.contains("rol=CLIENTE"));
        assertTrue(toString.contains("contrasena=password123"));
    }

    @Test
    void testNoArgsConstructor() {
        Usuario usuario = new Usuario();
        
        assertNull(usuario.getId());
        assertNull(usuario.getNombre());
        assertNull(usuario.getCorreo());
        assertNull(usuario.getRol());
        assertNull(usuario.getContrasena());
    }
} 