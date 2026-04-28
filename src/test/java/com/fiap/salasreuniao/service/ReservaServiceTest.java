package com.fiap.salasreuniao.service;

import com.fiap.salasreuniao.domain.Reserva;
import com.fiap.salasreuniao.domain.Sala;
import com.fiap.salasreuniao.dto.ReservaRequest;
import com.fiap.salasreuniao.exception.RegraNegocioException;
import com.fiap.salasreuniao.repository.ReservaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {
    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private SalaService salaService;

    @InjectMocks
    private ReservaService reservaService;

    @Test
    void deveCriarReservaQuandoNaoExisteConflito() {
        LocalDateTime inicio = LocalDateTime.now().plusDays(1);
        LocalDateTime fim = inicio.plusHours(1);
        ReservaRequest request = new ReservaRequest(1L, inicio, fim, "Matheus");
        Sala sala = new Sala(1L, "Sala Teste", 10, "1º andar");

        when(salaService.buscarPorId(1L)).thenReturn(sala);
        when(reservaRepository.existsConflito(1L, inicio, fim)).thenReturn(false);
        when(reservaRepository.save(any(Reserva.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Reserva reserva = reservaService.criar(request);

        assertEquals(sala, reserva.getSala());
        assertEquals("Matheus", reserva.getResponsavel());
        assertFalse(reserva.getCancelada());
    }

    @Test
    void naoDeveCriarReservaQuandoExisteConflito() {
        LocalDateTime inicio = LocalDateTime.now().plusDays(1);
        LocalDateTime fim = inicio.plusHours(1);
        ReservaRequest request = new ReservaRequest(1L, inicio, fim, "Matheus");
        Sala sala = new Sala(1L, "Sala Teste", 10, "1º andar");

        when(salaService.buscarPorId(1L)).thenReturn(sala);
        when(reservaRepository.existsConflito(1L, inicio, fim)).thenReturn(true);

        assertThrows(RegraNegocioException.class, () -> reservaService.criar(request));
        verify(reservaRepository, never()).save(any());
    }

    @Test
    void naoDeveAceitarDataFimAntesDoInicio() {
        LocalDateTime inicio = LocalDateTime.now().plusDays(1);
        LocalDateTime fim = inicio.minusHours(1);

        assertThrows(RegraNegocioException.class, () -> reservaService.validarIntervalo(inicio, fim));
    }
}
