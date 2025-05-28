package com.alquilerautos.service;

import com.alquilerautos.model.Pago;
import com.alquilerautos.model.Reserva;
import com.alquilerautos.model.Vehiculo;
import com.alquilerautos.repository.PagoRepository;
import com.alquilerautos.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private Validator validator;

    @InjectMocks
    private PagoService pagoService;

    private Pago pago;
    private Reserva reserva;
    private Vehiculo vehiculo;

    @BeforeEach
    void setUp() {
        vehiculo = new Vehiculo();
        vehiculo.setId(1L);
        vehiculo.setPrecioAlquilerDia(100.0);

        reserva = new Reserva();
        reserva.setId(1L);
        reserva.setVehiculo(vehiculo);
        reserva.setFechaInicio(LocalDate.now());
        reserva.setFechaFin(LocalDate.now().plusDays(1));

        pago = new Pago();
        pago.setId(1L);
        pago.setReserva(reserva);
        pago.setMonto(100.00);
        pago.setFechaPago(LocalDateTime.now());
        pago.setMetodoPago("TARJETA");
        pago.setEstado("COMPLETADO");
    }

    @Test
    void whenSavePago_thenReturnPago() {
        when(validator.validate(any())).thenReturn(new HashSet<>());
        when(pagoRepository.save(any(Pago.class))).thenReturn(pago);

        Pago saved = pagoService.save(pago);

        assertNotNull(saved);
        assertEquals(reserva.getId(), saved.getReserva().getId());
        assertEquals(100.00, saved.getMonto());
        assertEquals("TARJETA", saved.getMetodoPago());
        assertEquals("COMPLETADO", saved.getEstado());
        verify(pagoRepository).save(any(Pago.class));
        verify(validator).validate(any());
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
        when(validator.validate(any())).thenReturn(new HashSet<>());
        Reserva nuevaReserva = new Reserva();
        nuevaReserva.setId(2L);
        nuevaReserva.setVehiculo(vehiculo);
        nuevaReserva.setFechaInicio(LocalDate.now());
        nuevaReserva.setFechaFin(LocalDate.now().plusDays(1));

        Pago nuevoPago = new Pago();
        nuevoPago.setReserva(nuevaReserva);
        nuevoPago.setMonto(100.00);
        nuevoPago.setFechaPago(LocalDateTime.now().plusDays(1));
        nuevoPago.setMetodoPago("EFECTIVO");
        nuevoPago.setEstado("PENDIENTE");

        when(pagoRepository.findById(anyLong())).thenReturn(Optional.of(pago));
        when(pagoRepository.save(any(Pago.class))).thenReturn(nuevoPago);

        Pago updated = pagoService.update(1L, nuevoPago);

        assertNotNull(updated);
        assertEquals(2L, updated.getReserva().getId());
        assertEquals(100.00, updated.getMonto());
        assertEquals("EFECTIVO", updated.getMetodoPago());
        assertEquals("PENDIENTE", updated.getEstado());
        verify(pagoRepository).findById(anyLong());
        verify(pagoRepository).save(any(Pago.class));
        verify(validator).validate(any());
    }

    @Test
    void whenUpdatePagoNotFound_thenThrowException() {
        when(pagoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            pagoService.update(1L, new Pago());
        });

        verify(pagoRepository).findById(anyLong());
        verify(pagoRepository, never()).save(any(Pago.class));
    }

    @Test
    void whenDeletePago_thenVerifyRepositoryCall() {
        when(pagoRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(pagoRepository).deleteById(anyLong());

        pagoService.delete(1L);

        verify(pagoRepository).existsById(1L);
        verify(pagoRepository).deleteById(1L);
    }

    @Test
    void whenDeletePagoNotFound_thenThrowException() {
        when(pagoRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            pagoService.delete(1L);
        });

        verify(pagoRepository).existsById(1L);
        verify(pagoRepository, never()).deleteById(anyLong());
    }
} 