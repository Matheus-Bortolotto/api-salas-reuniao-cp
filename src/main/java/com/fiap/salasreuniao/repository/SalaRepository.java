package com.fiap.salasreuniao.repository;

import com.fiap.salasreuniao.domain.Sala;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepository extends JpaRepository<Sala, Long> {
    Page<Sala> findByNomeContainingIgnoreCaseOrLocalizacaoContainingIgnoreCase(String nome, String localizacao, Pageable pageable);
}
