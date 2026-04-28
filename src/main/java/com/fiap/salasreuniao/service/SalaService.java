package com.fiap.salasreuniao.service;

import com.fiap.salasreuniao.domain.Sala;
import com.fiap.salasreuniao.dto.SalaRequest;
import com.fiap.salasreuniao.exception.RecursoNaoEncontradoException;
import com.fiap.salasreuniao.repository.SalaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SalaService {
    private static final Logger log = LoggerFactory.getLogger(SalaService.class);
    private final SalaRepository repository;

    public SalaService(SalaRepository repository) {
        this.repository = repository;
    }

    @Cacheable("salas")
    public Page<Sala> listar(String busca, Pageable pageable) {
        if (busca == null || busca.isBlank()) {
            return repository.findAll(pageable);
        }
        return repository.findByNomeContainingIgnoreCaseOrLocalizacaoContainingIgnoreCase(busca, busca, pageable);
    }

    public Sala buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Sala não encontrada"));
    }

    @CacheEvict(value = "salas", allEntries = true)
    public Sala criar(SalaRequest request) {
        log.info("Criando sala: nome={}, capacidade={}, localizacao={}", request.nome(), request.capacidade(), request.localizacao());
        Sala sala = new Sala(null, request.nome(), request.capacidade(), request.localizacao());
        return repository.save(sala);
    }

    @CacheEvict(value = "salas", allEntries = true)
    public Sala atualizar(Long id, SalaRequest request) {
        Sala sala = buscarPorId(id);
        sala.setNome(request.nome());
        sala.setCapacidade(request.capacidade());
        sala.setLocalizacao(request.localizacao());
        return repository.save(sala);
    }

    @CacheEvict(value = "salas", allEntries = true)
    public void remover(Long id) {
        Sala sala = buscarPorId(id);
        repository.delete(sala);
    }
}
