package com.alquilerautos.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VehiculoTest {

    @Test
    void testConstructorAndGetters() {
        Vehiculo vehiculo = new Vehiculo(1L, "Toyota", "Corolla", "ABC123", 2022, 50.0, true);
        
        assertEquals(1L, vehiculo.getId());
        assertEquals("Toyota", vehiculo.getMarca());
        assertEquals("Corolla", vehiculo.getModelo());
        assertEquals("ABC123", vehiculo.getPlaca());
        assertEquals(2022, vehiculo.getAnio());
        assertEquals(50.0, vehiculo.getPrecioAlquilerDia());
        assertTrue(vehiculo.isDisponible());
    }

    @Test
    void testSetters() {
        Vehiculo vehiculo = new Vehiculo();
        
        vehiculo.setId(1L);
        vehiculo.setMarca("Toyota");
        vehiculo.setModelo("Corolla");
        vehiculo.setPlaca("ABC123");
        vehiculo.setAnio(2022);
        vehiculo.setPrecioAlquilerDia(50.0);
        vehiculo.setDisponible(true);

        assertEquals(1L, vehiculo.getId());
        assertEquals("Toyota", vehiculo.getMarca());
        assertEquals("Corolla", vehiculo.getModelo());
        assertEquals("ABC123", vehiculo.getPlaca());
        assertEquals(2022, vehiculo.getAnio());
        assertEquals(50.0, vehiculo.getPrecioAlquilerDia());
        assertTrue(vehiculo.isDisponible());
    }

    @Test
    void testEqualsAndHashCode() {
        Vehiculo vehiculo1 = new Vehiculo(1L, "Toyota", "Corolla", "ABC123", 2022, 50.0, true);
        Vehiculo vehiculo2 = new Vehiculo(1L, "Toyota", "Corolla", "ABC123", 2022, 50.0, true);
        Vehiculo vehiculo3 = new Vehiculo(2L, "Honda", "Civic", "XYZ789", 2023, 60.0, false);

        assertEquals(vehiculo1, vehiculo2);
        assertEquals(vehiculo1.hashCode(), vehiculo2.hashCode());
        assertNotEquals(vehiculo1, vehiculo3);
        assertNotEquals(vehiculo1.hashCode(), vehiculo3.hashCode());
    }

    @Test
    void testToString() {
        Vehiculo vehiculo = new Vehiculo(1L, "Toyota", "Corolla", "ABC123", 2022, 50.0, true);
        String toString = vehiculo.toString();

        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("marca=Toyota"));
        assertTrue(toString.contains("modelo=Corolla"));
        assertTrue(toString.contains("anio=2022"));
        assertTrue(toString.contains("placa=ABC123"));
        assertTrue(toString.contains("precioAlquilerDia=50.0"));
        assertTrue(toString.contains("disponible=true"));
    }

    @Test
    void testNoArgsConstructor() {
        Vehiculo vehiculo = new Vehiculo();
        
        assertNull(vehiculo.getId());
        assertNull(vehiculo.getMarca());
        assertNull(vehiculo.getModelo());
        assertNull(vehiculo.getPlaca());
        assertNull(vehiculo.getAnio());
        assertEquals(0.0, vehiculo.getPrecioAlquilerDia());
        assertFalse(vehiculo.isDisponible());
    }
} 