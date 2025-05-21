package com.alquilerautos.controller;

import com.alquilerautos.model.Pago;
import com.alquilerautos.model.Reserva;
import com.alquilerautos.service.PagoService;
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

@WebMvcTest(PagoController.class)
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
        pago.setFechaPago(LocalDate.now());
        pago.setMetodo("TARJETA");
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
                .andExpect(jsonPath("$.metodo").value("TARJETA"));
    }

    @Test
    void whenListarPagos_thenReturnList() throws Exception {
        List<Pago> pagos = Arrays.asList(pago);
        when(pagoService.getAll()).thenReturn(pagos);

        mockMvc.perform(get("/api/pagos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].reserva.id").value(1))
                .andExpect(jsonPath("$[0].monto").value(100.00))
                .andExpect(jsonPath("$[0].metodo").value("TARJETA"));
    }

    @Test
    void whenEditarPago_thenReturnUpdatedPago() throws Exception {
        Reserva nuevaReserva = new Reserva();
        nuevaReserva.setId(2L);

        Pago nuevoPago = new Pago();
        nuevoPago.setId(1L);
        nuevoPago.setReserva(nuevaReserva);
        nuevoPago.setMonto(150.00);
        nuevoPago.setFechaPago(LocalDate.now().plusDays(1));
        nuevoPago.setMetodo("EFECTIVO");

        when(pagoService.update(anyLong(), any(Pago.class))).thenReturn(nuevoPago);

        mockMvc.perform(put("/api/pagos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoPago)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reserva.id").value(2))
                .andExpect(jsonPath("$.monto").value(150.00))
                .andExpect(jsonPath("$.metodo").value("EFECTIVO"));
    }

    @Test
    void whenEliminarPago_thenReturn200() throws Exception {
        doNothing().when(pagoService).delete(anyLong());

        mockMvc.perform(delete("/api/pagos/1"))
                .andExpect(status().isOk());
    }
} 