package com.alquilerautos.controller;

import com.alquilerautos.model.Vehiculo;
import com.alquilerautos.service.VehiculoService;
import com.alquilerautos.config.TestSecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VehiculoController.class)
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
class VehiculoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehiculoService vehiculoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Vehiculo vehiculo;

    @BeforeEach
    void setUp() {
        vehiculo = new Vehiculo();
        vehiculo.setId(1L);
        vehiculo.setMarca("Toyota");
        vehiculo.setModelo("Corolla");
        vehiculo.setAnio(-2022);
        vehiculo.setPlaca("ABC123");
        vehiculo.setPrecioAlquilerDia(50.0);
        vehiculo.setDisponible(true);
    }

    @Test
    void whenRegistrarVehiculo_thenReturnVehiculo() throws Exception {
        when(vehiculoService.save(any(Vehiculo.class))).thenReturn(vehiculo);

        mockMvc.perform(post("/api/vehiculos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vehiculo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marca").value("Toyota"))
                .andExpect(jsonPath("$.modelo").value("Corolla"))
                .andExpect(jsonPath("$.placa").value("ABC123"))
                .andExpect(jsonPath("$.precioAlquilerDia").value(50.0))
                .andExpect(jsonPath("$.disponible").value(true));
    }

    @Test
    void whenListarVehiculos_thenReturnList() throws Exception {
        List<Vehiculo> vehiculos = Arrays.asList(vehiculo);
        when(vehiculoService.getAll()).thenReturn(vehiculos);

        mockMvc.perform(get("/api/vehiculos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].marca").value("Toyota"))
                .andExpect(jsonPath("$[0].modelo").value("Corolla"))
                .andExpect(jsonPath("$[0].placa").value("ABC123"))
                .andExpect(jsonPath("$[0].precioAlquilerDia").value(50.0))
                .andExpect(jsonPath("$[0].disponible").value(true));
    }

    @Test
    void whenBuscarVehiculo_thenReturnVehiculo() throws Exception {
        List<Vehiculo> vehiculos = Arrays.asList(vehiculo);
        when(vehiculoService.searchByPlaca(anyString())).thenReturn(vehiculos);

        mockMvc.perform(get("/api/vehiculos/buscar")
                .param("placa", "ABC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].marca").value("Toyota"))
                .andExpect(jsonPath("$[0].placa").value("ABC123"));
    }

    @Test
    void whenEditarVehiculo_thenReturnUpdatedVehiculo() throws Exception {
        Vehiculo vehiculoActualizado = new Vehiculo();
        vehiculoActualizado.setId(1L);
        vehiculoActualizado.setMarca("Honda");
        vehiculoActualizado.setModelo("Civic");
        vehiculoActualizado.setAnio(2023);
        vehiculoActualizado.setPlaca("XYZ789");
        vehiculoActualizado.setPrecioAlquilerDia(60.0);
        vehiculoActualizado.setDisponible(false);

        when(vehiculoService.update(anyLong(), any(Vehiculo.class))).thenReturn(vehiculoActualizado);

        mockMvc.perform(put("/api/vehiculos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vehiculoActualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marca").value("Honda"))
                .andExpect(jsonPath("$.modelo").value("Civic"))
                .andExpect(jsonPath("$.placa").value("XYZ789"))
                .andExpect(jsonPath("$.precioAlquilerDia").value(60.0))
                .andExpect(jsonPath("$.disponible").value(false));
    }

    @Test
    void whenEliminarVehiculo_thenReturn200() throws Exception {
        doNothing().when(vehiculoService).delete(anyLong());

        mockMvc.perform(delete("/api/vehiculos/1"))
                .andExpect(status().isOk());
    }

    @Test
    void whenEditarVehiculoInexistente_thenReturn404() throws Exception {
        when(vehiculoService.update(anyLong(), any(Vehiculo.class)))
            .thenThrow(new com.alquilerautos.exception.ResourceNotFoundException("Vehículo", "id", 1L));

        mockMvc.perform(put("/api/vehiculos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vehiculo)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Vehículo no encontrado con id: '1'"));
    }

    @Test
    void whenEliminarVehiculoInexistente_thenReturn404() throws Exception {
        doThrow(new com.alquilerautos.exception.ResourceNotFoundException("Vehículo", "id", 1L))
            .when(vehiculoService).delete(anyLong());

        mockMvc.perform(delete("/api/vehiculos/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Vehículo no encontrado con id: '1'"));
    }
} 