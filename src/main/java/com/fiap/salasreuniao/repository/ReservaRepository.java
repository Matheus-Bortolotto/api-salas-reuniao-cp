package com.fiap.salasreuniao.repository;

import com.fiap.salasreuniao.domain.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    @Query("""
            select count(r) > 0 from Reserva r
            where r.sala.id = :salaId
              and r.cancelada = false
              and r.dataHoraInicio < :fim
              and r.dataHoraFim > :inicio
            """)
    boolean existsConflito(@Param("salaId") Long salaId,
                           @Param("inicio") LocalDateTime inicio,
                           @Param("fim") LocalDateTime fim);

    Page<Reserva> findBySalaIdAndCanceladaFalse(Long salaId, Pageable pageable);

    Page<Reserva> findByDataHoraInicioBetweenAndCanceladaFalse(LocalDateTime inicio, LocalDateTime fim, Pageable pageable);
}
