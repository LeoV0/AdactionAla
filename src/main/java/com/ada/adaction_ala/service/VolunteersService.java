package com.ada.adaction_ala.service;

import com.ada.adaction_ala.model.Volunteers;
import com.ada.adaction_ala.repository.VolunteersRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class VolunteersService {

    private static final Logger logger = LoggerFactory.getLogger(VolunteersService.class);

    private final VolunteersRepository volunteersRepository;
    private final ObjectMapper objectMapper;

    public VolunteersService(VolunteersRepository volunteersRepository, ObjectMapper objectMapper) {
        this.volunteersRepository = volunteersRepository;
        this.objectMapper = objectMapper;
    }

    public Optional<Volunteers> findVolunteersById(Long id) {
        return volunteersRepository.findById(id);
    }

    public Iterable<Volunteers> findAllVolunteers() {
        return volunteersRepository.findAll();
    }

    public void deleteVolunteers(Long id) {
        volunteersRepository.deleteById(id);
    }

    public Volunteers saveVolunteers(Volunteers volunteers) {
        return volunteersRepository.save(volunteers);
    }

    public void importVolunteersFromJson() throws IOException {
        logger.info("Starting import of associations from JSON");
        ClassPathResource resource = new ClassPathResource("data/volunteers.json");
        if (!resource.exists()) {
            logger.error("File data/volunteers.json not found in classpath");
            throw new IOException("File data/volunteers.json not found");
        }
        String jsonContent = new String(resource.getInputStream().readAllBytes());
        logger.info("JSON content: {}", jsonContent);
        try {
            List<Volunteers> volunteers = objectMapper.readValue(
                jsonContent,
                new TypeReference<List<Volunteers>>() {}
            );
            logger.info("Imported {} volunteers from JSON", volunteers.size());
            volunteersRepository.saveAll(volunteers);
            logger.info("Volunteers saved to database");
        } catch (Exception e) {
            logger.error("Failed to deserialize JSON: {}", e.getMessage());
            throw e;
        }
    }
}