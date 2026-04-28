package com.fiap.salasreuniao.controller;

import com.fiap.salasreuniao.domain.Sala;
import com.fiap.salasreuniao.dto.SalaRequest;
import com.fiap.salasreuniao.service.SalaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/salas")
public class SalaController {
    private final SalaService service;

    public SalaController(SalaService service) {
        this.service = service;
    }

    @Operation(summary = "Listar salas com paginação e filtro por nome/localização")
    @GetMapping
    public Page<Sala> listar(@RequestParam(required = false) String busca, Pageable pageable) {
        return service.listar(busca, pageable);
    }

    @Operation(summary = "Buscar sala por ID")
    @GetMapping("/{id}")
    public Sala buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @Operation(summary = "Criar sala")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Sala criar(@Valid @RequestBody SalaRequest request) {
        return service.criar(request);
    }

    @Operation(summary = "Atualizar sala")
    @PutMapping("/{id}")
    public Sala atualizar(@PathVariable Long id, @Valid @RequestBody SalaRequest request) {
        return service.atualizar(id, request);
    }

    @Operation(summary = "Remover sala")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.remover(id);
    }
}
