package com.alquilerautos.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class PagoTest {

    @Test
    void testConstructorAndGetters() {
        Reserva reserva = new Reserva();
        LocalDateTime fechaPago = LocalDateTime.now();
        
        Pago pago = new Pago(1L, reserva, 1000.0, fechaPago, "TARJETA", "COMPLETADO");
        
        assertEquals(1L, pago.getId());
        assertEquals(reserva, pago.getReserva());
        assertEquals(1000.0, pago.getMonto());
        assertEquals(fechaPago, pago.getFechaPago());
        assertEquals("TARJETA", pago.getMetodoPago());
        assertEquals("COMPLETADO", pago.getEstado());
    }

    @Test
    void testSetters() {
        Pago pago = new Pago();
        Reserva reserva = new Reserva();
        LocalDateTime fechaPago = LocalDateTime.now();
        
        pago.setId(1L);
        pago.setReserva(reserva);
        pago.setMonto(1000.0);
        pago.setFechaPago(fechaPago);
        pago.setMetodoPago("TARJETA");
        pago.setEstado("COMPLETADO");
        
        assertEquals(1L, pago.getId());
        assertEquals(reserva, pago.getReserva());
        assertEquals(1000.0, pago.getMonto());
        assertEquals(fechaPago, pago.getFechaPago());
        assertEquals("TARJETA", pago.getMetodoPago());
        assertEquals("COMPLETADO", pago.getEstado());
    }

    @Test
    void testEquals() {
        Pago pago1 = new Pago(1L, null, 1000.0, null, "TARJETA", "COMPLETADO");
        Pago pago2 = new Pago(1L, null, 1000.0, null, "TARJETA", "COMPLETADO");
        Pago pago3 = new Pago(2L, null, 1000.0, null, "EFECTIVO", "PENDIENTE");

        assertEquals(pago1, pago2);
        assertNotEquals(pago1, pago3);
        assertNotEquals(pago1, null);
        assertNotEquals(pago1, new Object());
    }

    @Test
    void testHashCode() {
        Pago pago1 = new Pago(1L, null, 1000.0, null, "TARJETA", "COMPLETADO");
        Pago pago2 = new Pago(1L, null, 1000.0, null, "TARJETA", "COMPLETADO");

        assertEquals(pago1.hashCode(), pago2.hashCode());
    }

    @Test
    void testToString() {
        Pago pago = new Pago(1L, null, 1000.0, LocalDateTime.of(2024, 3, 20, 10, 0), "TARJETA", "COMPLETADO");
        
        String toString = pago.toString();
        
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("monto=1000.0"));
        assertTrue(toString.contains("fechaPago=2024-03-20T10:00"));
        assertTrue(toString.contains("metodoPago=TARJETA"));
        assertTrue(toString.contains("estado=COMPLETADO"));
    }
} 