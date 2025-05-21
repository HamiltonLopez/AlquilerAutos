package com.alquilerautos.service;

import com.alquilerautos.model.Pago;
import com.alquilerautos.model.Reserva;
import com.alquilerautos.repository.PagoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @InjectMocks
    private PagoService pagoService;

    private Pago pago;
    private Reserva reserva;

    @BeforeEach
    void setUp() {
        reserva = new Reserva();
        reserva.setId(1L);

        pago = new Pago();
        pago.setId(1L);
        pago.setReserva(reserva);
        pago.setMonto(100.00);
        pago.setFechaPago(LocalDate.now());
        pago.setMetodo("TARJETA");
    }

    @Test
    void whenSavePago_thenReturnPago() {
        when(pagoRepository.save(any(Pago.class))).thenReturn(pago);

        Pago saved = pagoService.save(pago);

        assertNotNull(saved);
        assertEquals(reserva.getId(), saved.getReserva().getId());
        assertEquals(100.00, saved.getMonto());
        assertEquals("TARJETA", saved.getMetodo());
        verify(pagoRepository).save(any(Pago.class));
    }

    @Test
    void whenGetAll_thenReturnList() {
        List<Pago> pagos = Arrays.asList(pago);
        when(pagoRepository.findAll()).thenReturn(pagos);

        List<Pago> found = pagoService.getAll();

        assertNotNull(found);
        assertEquals(1, found.size());
        verify(pagoRepository).findAll();
    }

    @Test
    void whenUpdatePago_thenReturnUpdatedPago() {
        Reserva nuevaReserva = new Reserva();
        nuevaReserva.setId(2L);

        Pago nuevoPago = new Pago();
        nuevoPago.setReserva(nuevaReserva);
        nuevoPago.setMonto(150.00);
        nuevoPago.setFechaPago(LocalDate.now().plusDays(1));
        nuevoPago.setMetodo("EFECTIVO");

        when(pagoRepository.findById(anyLong())).thenReturn(Optional.of(pago));
        when(pagoRepository.save(any(Pago.class))).thenReturn(nuevoPago);

        Pago updated = pagoService.update(1L, nuevoPago);

        assertNotNull(updated);
        assertEquals(nuevaReserva.getId(), updated.getReserva().getId());
        assertEquals(150.00, updated.getMonto());
        assertEquals("EFECTIVO", updated.getMetodo());
        verify(pagoRepository).findById(anyLong());
        verify(pagoRepository).save(any(Pago.class));
    }

    @Test
    void whenDeletePago_thenVerifyRepositoryCall() {
        pagoService.delete(1L);
        verify(pagoRepository).deleteById(1L);
    }
} 