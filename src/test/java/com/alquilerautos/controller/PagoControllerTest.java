package com.alquilerautos.controller;

import com.alquilerautos.model.Pago;
import com.alquilerautos.model.Reserva;
import com.alquilerautos.service.PagoService;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PagoController.class)
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
class PagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PagoService pagoService;

    @Autowired
    private ObjectMapper objectMapper;

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
        pago.setFechaPago(LocalDateTime.now());
        pago.setMetodoPago("TARJETA");
        pago.setEstado("COMPLETADO");
    }

    @Test
    void whenRegistrarPago_thenReturnPago() throws Exception {
        when(pagoService.save(any(Pago.class))).thenReturn(pago);

        mockMvc.perform(post("/api/pagos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pago)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reserva.id").value(1))
                .andExpect(jsonPath("$.monto").value(100.00))
                .andExpect(jsonPath("$.metodoPago").value("TARJETA"))
                .andExpect(jsonPath("$.estado").value("COMPLETADO"));
    }

    @Test
    void whenListarPagos_thenReturnList() throws Exception {
        List<Pago> pagos = Arrays.asList(pago);
        when(pagoService.getAll()).thenReturn(pagos);

        mockMvc.perform(get("/api/pagos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].reserva.id").value(1))
                .andExpect(jsonPath("$[0].monto").value(100.00))
                .andExpect(jsonPath("$[0].metodoPago").value("TARJETA"))
                .andExpect(jsonPath("$[0].estado").value("COMPLETADO"));
    }

    @Test
    void whenEditarPago_thenReturnUpdatedPago() throws Exception {
        Reserva nuevaReserva = new Reserva();
        nuevaReserva.setId(2L);

        Pago nuevoPago = new Pago();
        nuevoPago.setId(1L);
        nuevoPago.setReserva(nuevaReserva);
        nuevoPago.setMonto(150.00);
        nuevoPago.setFechaPago(LocalDateTime.now().plusDays(1));
        nuevoPago.setMetodoPago("EFECTIVO");
        nuevoPago.setEstado("PENDIENTE");

        when(pagoService.update(anyLong(), any(Pago.class))).thenReturn(nuevoPago);

        mockMvc.perform(put("/api/pagos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoPago)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reserva.id").value(2))
                .andExpect(jsonPath("$.monto").value(150.00))
                .andExpect(jsonPath("$.metodoPago").value("EFECTIVO"))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));
    }

    @Test
    void whenEliminarPago_thenReturn200() throws Exception {
        doNothing().when(pagoService).delete(anyLong());

        mockMvc.perform(delete("/api/pagos/1"))
                .andExpect(status().isOk());
    }

    @Test
    void whenEditarPagoInexistente_thenReturn404() throws Exception {
        when(pagoService.update(anyLong(), any(Pago.class)))
            .thenThrow(new com.alquilerautos.exception.ResourceNotFoundException("Pago", "id", 1L));

        mockMvc.perform(put("/api/pagos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pago)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Pago no encontrado con id: '1'"));
    }

    @Test
    void whenEliminarPagoInexistente_thenReturn404() throws Exception {
        doThrow(new com.alquilerautos.exception.ResourceNotFoundException("Pago", "id", 1L))
            .when(pagoService).delete(anyLong());

        mockMvc.perform(delete("/api/pagos/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Pago no encontrado con id: '1'"));
    }
} 