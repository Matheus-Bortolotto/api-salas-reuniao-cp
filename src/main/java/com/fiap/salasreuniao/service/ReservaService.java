package com.fiap.salasreuniao.service;

import com.fiap.salasreuniao.domain.Reserva;
import com.fiap.salasreuniao.domain.Sala;
import com.fiap.salasreuniao.dto.ReservaRequest;
import com.fiap.salasreuniao.exception.RecursoNaoEncontradoException;
import com.fiap.salasreuniao.exception.RegraNegocioException;
import com.fiap.salasreuniao.repository.ReservaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReservaService {
    private static final Logger log = LoggerFactory.getLogger(ReservaService.class);
    private final ReservaRepository reservaRepository;
    private final SalaService salaService;

    public ReservaService(ReservaRepository reservaRepository, SalaService salaService) {
        this.reservaRepository = reservaRepository;
        this.salaService = salaService;
    }

    public Reserva criar(ReservaRequest request) {
        validarIntervalo(request.dataHoraInicio(), request.dataHoraFim());

        Sala sala = salaService.buscarPorId(request.salaId());
        boolean conflito = reservaRepository.existsConflito(
                request.salaId(), request.dataHoraInicio(), request.dataHoraFim());

        if (conflito) {
            throw new RegraNegocioException("Já existe uma reserva para esta sala no horário informado");
        }

        log.info("Criando reserva: salaId={}, inicio={}, fim={}, responsavel={}",
                request.salaId(), request.dataHoraInicio(), request.dataHoraFim(), request.responsavel());

        Reserva reserva = new Reserva(null, sala, request.dataHoraInicio(), request.dataHoraFim(), request.responsavel(), false);
        return reservaRepository.save(reserva);
    }

    public Page<Reserva> listar(Long salaId, LocalDateTime inicio, LocalDateTime fim, Pageable pageable) {
        if (salaId != null) {
            return reservaRepository.findBySalaIdAndCanceladaFalse(salaId, pageable);
        }
        if (inicio != null && fim != null) {
            validarIntervalo(inicio, fim);
            return reservaRepository.findByDataHoraInicioBetweenAndCanceladaFalse(inicio, fim, pageable);
        }
        return reservaRepository.findAll(pageable);
    }

    public void cancelar(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Reserva não encontrada"));
        reserva.setCancelada(true);
        reservaRepository.save(reserva);
        log.info("Reserva cancelada: id={}", id);
    }

    void validarIntervalo(LocalDateTime inicio, LocalDateTime fim) {
        if (!fim.isAfter(inicio)) {
            throw new RegraNegocioException("A data/hora final deve ser posterior à data/hora inicial");
        }
    }
}
