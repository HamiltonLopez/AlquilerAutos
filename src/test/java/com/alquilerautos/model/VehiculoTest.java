package com.alquilerautos.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VehiculoTest {

    @Test
    void testConstructorAndGetters() {
        Vehiculo vehiculo = new Vehiculo(1L, "Toyota", "Corolla", 2022, "ABC123", "Sedán");
        
        assertEquals(1L, vehiculo.getId());
        assertEquals("Toyota", vehiculo.getMarca());
        assertEquals("Corolla", vehiculo.getModelo());
        assertEquals(2022, vehiculo.getAnio());
        assertEquals("ABC123", vehiculo.getPlaca());
        assertEquals("Sedán", vehiculo.getTipo());
    }

    @Test
    void testSetters() {
        Vehiculo vehiculo = new Vehiculo();
        
        vehiculo.setId(1L);
        vehiculo.setMarca("Toyota");
        vehiculo.setModelo("Corolla");
        vehiculo.setAnio(2022);
        vehiculo.setPlaca("ABC123");
        vehiculo.setTipo("Sedán");

        assertEquals(1L, vehiculo.getId());
        assertEquals("Toyota", vehiculo.getMarca());
        assertEquals("Corolla", vehiculo.getModelo());
        assertEquals(2022, vehiculo.getAnio());
        assertEquals("ABC123", vehiculo.getPlaca());
        assertEquals("Sedán", vehiculo.getTipo());
    }

    @Test
    void testEqualsAndHashCode() {
        Vehiculo vehiculo1 = new Vehiculo(1L, "Toyota", "Corolla", 2022, "ABC123", "Sedán");
        Vehiculo vehiculo2 = new Vehiculo(1L, "Toyota", "Corolla", 2022, "ABC123", "Sedán");
        Vehiculo vehiculo3 = new Vehiculo(2L, "Honda", "Civic", 2023, "XYZ789", "Sedán");

        assertEquals(vehiculo1, vehiculo2);
        assertEquals(vehiculo1.hashCode(), vehiculo2.hashCode());
        assertNotEquals(vehiculo1, vehiculo3);
        assertNotEquals(vehiculo1.hashCode(), vehiculo3.hashCode());
    }

    @Test
    void testToString() {
        Vehiculo vehiculo = new Vehiculo(1L, "Toyota", "Corolla", 2022, "ABC123", "Sedán");
        String toString = vehiculo.toString();

        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("marca=Toyota"));
        assertTrue(toString.contains("modelo=Corolla"));
        assertTrue(toString.contains("anio=2022"));
        assertTrue(toString.contains("placa=ABC123"));
        assertTrue(toString.contains("tipo=Sedán"));
    }

    @Test
    void testNoArgsConstructor() {
        Vehiculo vehiculo = new Vehiculo();
        
        assertNull(vehiculo.getId());
        assertNull(vehiculo.getMarca());
        assertNull(vehiculo.getModelo());
        assertEquals(0, vehiculo.getAnio());
        assertNull(vehiculo.getPlaca());
        assertNull(vehiculo.getTipo());
    }
} 