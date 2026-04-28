package com.fiap.salasreuniao.config;

import com.fiap.salasreuniao.domain.Sala;
import com.fiap.salasreuniao.repository.SalaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private final SalaRepository salaRepository;

    public DataInitializer(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    @Override
    public void run(String... args) {
        if (salaRepository.count() == 0) {
            salaRepository.save(new Sala(null, "Sala Inovação", 8, "1º andar"));
            salaRepository.save(new Sala(null, "Sala Estratégia", 12, "2º andar"));
            salaRepository.save(new Sala(null, "Auditório", 50, "Térreo"));
        }
    }
}
