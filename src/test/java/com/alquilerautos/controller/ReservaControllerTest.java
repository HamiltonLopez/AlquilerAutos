package com.alquilerautos.controller;

import com.alquilerautos.model.Reserva;
import com.alquilerautos.model.Usuario;
import com.alquilerautos.model.Vehiculo;
import com.alquilerautos.service.ReservaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservaController.class)
class ReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservaService reservaService;

    @Autowired
    private ObjectMapper objectMapper;

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

        reserva = new Reserva();
        reserva.setId(1L);
        reserva.setUsuario(usuario);
        reserva.setVehiculo(vehiculo);
        reserva.setFechaInicio(LocalDate.now());
        reserva.setFechaFin(LocalDate.now().plusDays(7));
        reserva.setEstado("PENDIENTE");
    }

    @Test
    void whenRegistrarReserva_thenReturnReserva() throws Exception {
        when(reservaService.save(any(Reserva.class))).thenReturn(reserva);

        mockMvc.perform(post("/api/reservas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reserva)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usuario.id").value(1))
                .andExpect(jsonPath("$.vehiculo.id").value(1))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));
    }

    @Test
    void whenListarReservas_thenReturnList() throws Exception {
        List<Reserva> reservas = Arrays.asList(reserva);
        when(reservaService.getAll()).thenReturn(reservas);

        mockMvc.perform(get("/api/reservas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].usuario.id").value(1))
                .andExpect(jsonPath("$[0].vehiculo.id").value(1))
                .andExpect(jsonPath("$[0].estado").value("PENDIENTE"));
    }

    @Test
    void whenEditarReserva_thenReturnUpdatedReserva() throws Exception {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setId(2L);
        nuevoUsuario.setNombre("María López");

        Vehiculo nuevoVehiculo = new Vehiculo();
        nuevoVehiculo.setId(2L);
        nuevoVehiculo.setMarca("Honda");
        nuevoVehiculo.setModelo("Civic");

        Reserva nuevaReserva = new Reserva();
        nuevaReserva.setId(1L);
        nuevaReserva.setUsuario(nuevoUsuario);
        nuevaReserva.setVehiculo(nuevoVehiculo);
        nuevaReserva.setFechaInicio(LocalDate.now().plusDays(1));
        nuevaReserva.setFechaFin(LocalDate.now().plusDays(8));
        nuevaReserva.setEstado("CONFIRMADA");

        when(reservaService.update(anyLong(), any(Reserva.class))).thenReturn(nuevaReserva);

        mockMvc.perform(put("/api/reservas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevaReserva)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usuario.id").value(2))
                .andExpect(jsonPath("$.vehiculo.id").value(2))
                .andExpect(jsonPath("$.estado").value("CONFIRMADA"));
    }

    @Test
    void whenEliminarReserva_thenReturn200() throws Exception {
        doNothing().when(reservaService).delete(anyLong());

        mockMvc.perform(delete("/api/reservas/1"))
                .andExpect(status().isOk());
    }
} 