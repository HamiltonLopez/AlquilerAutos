package com.alquilerautos.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class PagoTest {

    @Test
    void testConstructorAndGetters() {
        Reserva reserva = new Reserva();
        LocalDate fechaPago = LocalDate.now();
        
        Pago pago = new Pago(1L, reserva, 1000.0, fechaPago, "TARJETA");
        
        assertEquals(1L, pago.getId());
        assertEquals(reserva, pago.getReserva());
        assertEquals(1000.0, pago.getMonto());
        assertEquals(fechaPago, pago.getFechaPago());
        assertEquals("TARJETA", pago.getMetodo());
    }

    @Test
    void testSetters() {
        Pago pago = new Pago();
        Reserva reserva = new Reserva();
        LocalDate fechaPago = LocalDate.now();
        
        pago.setId(1L);
        pago.setReserva(reserva);
        pago.setMonto(1000.0);
        pago.setFechaPago(fechaPago);
        pago.setMetodo("TARJETA");
        
        assertEquals(1L, pago.getId());
        assertEquals(reserva, pago.getReserva());
        assertEquals(1000.0, pago.getMonto());
        assertEquals(fechaPago, pago.getFechaPago());
        assertEquals("TARJETA", pago.getMetodo());
    }

    @Test
    void testEquals() {
        Pago pago1 = new Pago(1L, null, 1000.0, null, "TARJETA");
        Pago pago2 = new Pago(1L, null, 1000.0, null, "TARJETA");
        Pago pago3 = new Pago(2L, null, 1000.0, null, "TARJETA");

        assertEquals(pago1, pago2);
        assertNotEquals(pago1, pago3);
        assertNotEquals(pago1, null);
        assertNotEquals(pago1, new Object());
    }

    @Test
    void testHashCode() {
        Pago pago1 = new Pago(1L, null, 1000.0, null, "TARJETA");
        Pago pago2 = new Pago(1L, null, 1000.0, null, "TARJETA");

        assertEquals(pago1.hashCode(), pago2.hashCode());
    }

    @Test
    void testToString() {
        Pago pago = new Pago(1L, null, 1000.0, LocalDate.of(2024, 3, 20), "TARJETA");
        
        String toString = pago.toString();
        
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("monto=1000.0"));
        assertTrue(toString.contains("fechaPago=2024-03-20"));
        assertTrue(toString.contains("metodo=TARJETA"));
    }
} 