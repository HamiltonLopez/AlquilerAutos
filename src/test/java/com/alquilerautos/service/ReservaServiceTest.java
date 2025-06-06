package com.alquilerautos.service;

import com.alquilerautos.model.Reserva;
import com.alquilerautos.model.Usuario;
import com.alquilerautos.model.Vehiculo;
import com.alquilerautos.repository.ReservaRepository;
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
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private Validator validator;

    @InjectMocks
    private ReservaService reservaService;

    private Reserva reserva;
    private Usuario usuario;
    private Vehiculo vehiculo;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan Pérez");

        vehiculo = new Vehiculo();
        vehiculo.setId(1L);
        vehiculo.setMarca("Toyota");
        vehiculo.setModelo("Corolla");
        vehiculo.setDisponible(true);

        reserva = new Reserva();
        reserva.setId(1L);
        reserva.setUsuario(usuario);
        reserva.setVehiculo(vehiculo);
        reserva.setFechaInicio(LocalDate.now());
        reserva.setFechaFin(LocalDate.now().plusDays(7));
        reserva.setEstado("PENDIENTE");
    }

    @Test
    void whenSaveReserva_thenReturnReserva() {
        when(validator.validate(any())).thenReturn(new HashSet<>());
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);

        Reserva saved = reservaService.save(reserva);

        assertNotNull(saved);
        assertEquals(usuario.getId(), saved.getUsuario().getId());
        assertEquals(vehiculo.getId(), saved.getVehiculo().getId());
        assertEquals("PENDIENTE", saved.getEstado());
        verify(reservaRepository).save(any(Reserva.class));
        verify(validator).validate(any());
    }

    @Test
    void whenGetAll_thenReturnList() {
        List<Reserva> reservas = Arrays.asList(reserva);
        when(reservaRepository.findAll()).thenReturn(reservas);

        List<Reserva> found = reservaService.getAll();

        assertNotNull(found);
        assertEquals(1, found.size());
        verify(reservaRepository).findAll();
    }

    @Test
    void whenUpdateReserva_thenReturnUpdatedReserva() {
        when(validator.validate(any())).thenReturn(new HashSet<>());
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setId(2L);
        nuevoUsuario.setNombre("María López");

        Vehiculo nuevoVehiculo = new Vehiculo();
        nuevoVehiculo.setId(2L);
        nuevoVehiculo.setMarca("Honda");
        nuevoVehiculo.setModelo("Civic");
        nuevoVehiculo.setDisponible(true);

        Reserva nuevaReserva = new Reserva();
        nuevaReserva.setUsuario(nuevoUsuario);
        nuevaReserva.setVehiculo(nuevoVehiculo);
        nuevaReserva.setFechaInicio(LocalDate.now().plusDays(1));
        nuevaReserva.setFechaFin(LocalDate.now().plusDays(8));
        nuevaReserva.setEstado("CONFIRMADA");

        when(reservaRepository.findById(anyLong())).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(any(Reserva.class))).thenReturn(nuevaReserva);

        Reserva updated = reservaService.update(1L, nuevaReserva);

        assertNotNull(updated);
        assertEquals(nuevoUsuario.getId(), updated.getUsuario().getId());
        assertEquals(nuevoVehiculo.getId(), updated.getVehiculo().getId());
        assertEquals("CONFIRMADA", updated.getEstado());
        verify(reservaRepository).findById(anyLong());
        verify(reservaRepository).save(any(Reserva.class));
        verify(validator).validate(any());
    }

    @Test
    void whenUpdateReservaNotFound_thenThrowException() {
        when(reservaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            reservaService.update(1L, new Reserva());
        });

        verify(reservaRepository).findById(anyLong());
        verify(reservaRepository, never()).save(any(Reserva.class));
    }

    @Test
    void whenDeleteReserva_thenVerifyRepositoryCall() {
        when(reservaRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(reservaRepository).deleteById(anyLong());

        reservaService.delete(1L);

        verify(reservaRepository).existsById(1L);
        verify(reservaRepository).deleteById(1L);
    }

    @Test
    void whenDeleteReservaNotFound_thenThrowException() {
        when(reservaRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            reservaService.delete(1L);
        });

        verify(reservaRepository).existsById(1L);
        verify(reservaRepository, never()).deleteById(anyLong());
    }
} 