package com.alquilerautos.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class ReservaTest {

    @Test
    void testConstructorAndGetters() {
        Usuario usuario = new Usuario();
        Vehiculo vehiculo = new Vehiculo();
        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaFin = LocalDate.now().plusDays(5);
        
        Reserva reserva = new Reserva(1L, usuario, vehiculo, fechaInicio, fechaFin, "ACTIVA");
        
        assertEquals(1L, reserva.getId());
        assertEquals(usuario, reserva.getUsuario());
        assertEquals(vehiculo, reserva.getVehiculo());
        assertEquals(fechaInicio, reserva.getFechaInicio());
        assertEquals(fechaFin, reserva.getFechaFin());
        assertEquals("ACTIVA", reserva.getEstado());
    }

    @Test
    void testSetters() {
        Reserva reserva = new Reserva();
        Usuario usuario = new Usuario();
        Vehiculo vehiculo = new Vehiculo();
        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaFin = LocalDate.now().plusDays(5);
        
        reserva.setId(1L);
        reserva.setUsuario(usuario);
        reserva.setVehiculo(vehiculo);
        reserva.setFechaInicio(fechaInicio);
        reserva.setFechaFin(fechaFin);
        reserva.setEstado("ACTIVA");
        
        assertEquals(1L, reserva.getId());
        assertEquals(usuario, reserva.getUsuario());
        assertEquals(vehiculo, reserva.getVehiculo());
        assertEquals(fechaInicio, reserva.getFechaInicio());
        assertEquals(fechaFin, reserva.getFechaFin());
        assertEquals("ACTIVA", reserva.getEstado());
    }

    @Test
    void testEquals() {
        Reserva reserva1 = new Reserva(1L, null, null, null, null, "ACTIVA");
        Reserva reserva2 = new Reserva(1L, null, null, null, null, "ACTIVA");
        Reserva reserva3 = new Reserva(2L, null, null, null, null, "ACTIVA");

        assertEquals(reserva1, reserva2);
        assertNotEquals(reserva1, reserva3);
        assertNotEquals(reserva1, null);
        assertNotEquals(reserva1, new Object());
    }

    @Test
    void testHashCode() {
        Reserva reserva1 = new Reserva(1L, null, null, null, null, "ACTIVA");
        Reserva reserva2 = new Reserva(1L, null, null, null, null, "ACTIVA");

        assertEquals(reserva1.hashCode(), reserva2.hashCode());
    }

    @Test
    void testToString() {
        Reserva reserva = new Reserva(1L, null, null, LocalDate.of(2024, 3, 20), 
            LocalDate.of(2024, 3, 25), "ACTIVA");
        
        String toString = reserva.toString();
        
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("estado=ACTIVA"));
        assertTrue(toString.contains("fechaInicio=2024-03-20"));
        assertTrue(toString.contains("fechaFin=2024-03-25"));
    }
} 