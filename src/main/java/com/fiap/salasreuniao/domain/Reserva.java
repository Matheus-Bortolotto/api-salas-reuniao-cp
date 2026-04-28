package com.fiap.salasreuniao.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sala_id")
    private Sala sala;

    @Column(nullable = false)
    private LocalDateTime dataHoraInicio;

    @Column(nullable = false)
    private LocalDateTime dataHoraFim;

    @Column(nullable = false)
    private String responsavel;

    @Column(nullable = false)
    private Boolean cancelada = false;

    public Reserva() {}

    public Reserva(Long id, Sala sala, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, String responsavel, Boolean cancelada) {
        this.id = id;
        this.sala = sala;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.responsavel = responsavel;
        this.cancelada = cancelada;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Sala getSala() { return sala; }
    public void setSala(Sala sala) { this.sala = sala; }
    public LocalDateTime getDataHoraInicio() { return dataHoraInicio; }
    public void setDataHoraInicio(LocalDateTime dataHoraInicio) { this.dataHoraInicio = dataHoraInicio; }
    public LocalDateTime getDataHoraFim() { return dataHoraFim; }
    public void setDataHoraFim(LocalDateTime dataHoraFim) { this.dataHoraFim = dataHoraFim; }
    public String getResponsavel() { return responsavel; }
    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }
    public Boolean getCancelada() { return cancelada; }
    public void setCancelada(Boolean cancelada) { this.cancelada = cancelada; }
}
