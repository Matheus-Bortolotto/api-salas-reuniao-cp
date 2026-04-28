package com.fiap.salasreuniao.controller;

import com.fiap.salasreuniao.domain.Reserva;
import com.fiap.salasreuniao.dto.ReservaRequest;
import com.fiap.salasreuniao.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {
    private final ReservaService service;

    public ReservaController(ReservaService service) {
        this.service = service;
    }

    @Operation(summary = "Criar reserva sem conflito de horário")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reserva criar(@Valid @RequestBody ReservaRequest request) {
        return service.criar(request);
    }

    @Operation(summary = "Listar reservas com paginação e filtros por sala ou período")
    @GetMapping
    public Page<Reserva> listar(
            @RequestParam(required = false) Long salaId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim,
            Pageable pageable) {
        return service.listar(salaId, inicio, fim, pageable);
    }

    @Operation(summary = "Cancelar reserva")
    @PatchMapping("/{id}/cancelar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable Long id) {
        service.cancelar(id);
    }
}
