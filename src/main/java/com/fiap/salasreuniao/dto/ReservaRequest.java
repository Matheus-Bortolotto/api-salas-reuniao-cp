package com.fiap.salasreuniao.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ReservaRequest(
        @NotNull Long salaId,
        @NotNull @Future LocalDateTime dataHoraInicio,
        @NotNull @Future LocalDateTime dataHoraFim,
        @NotBlank String responsavel
) {}
