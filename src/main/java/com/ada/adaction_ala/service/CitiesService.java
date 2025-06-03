package com.ada.adaction_ala.service;

import com.ada.adaction_ala.model.Cities;
import com.ada.adaction_ala.repository.CitiesRepository;
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
public class CitiesService {

    private static final Logger logger = LoggerFactory.getLogger(CitiesService.class);

    private final CitiesRepository citiesRepository;
    private final ObjectMapper objectMapper;

    public CitiesService(CitiesRepository citiesRepository, ObjectMapper objectMapper) {
        this.citiesRepository = citiesRepository;
        this.objectMapper = objectMapper;
    }

    public Optional<Cities> findCitiesById(Long id) {
        return citiesRepository.findById(id);
    }

    public Iterable<Cities> findAllCities() {
        return citiesRepository.findAll();
    }

    public void deleteCities(Long id) {
        citiesRepository.deleteById(id);
    }

    public Cities saveCities(Cities cities) {
        return citiesRepository.save(cities);
    }

    public void importCitiesFromJson() throws IOException {
        logger.info("Starting import of cities from JSON");
        ClassPathResource resource = new ClassPathResource("data/cities.json");
        if (!resource.exists()) {
            logger.error("File data/cities.json not found in classpath");
            throw new IOException("File data/cities.json not found");
        }
        String jsonContent = new String(resource.getInputStream().readAllBytes());
        logger.info("JSON content: {}", jsonContent);
        try {
            List<Cities> cities = objectMapper.readValue(
                jsonContent,
                new TypeReference<List<Cities>>() {}
            );
            logger.info("Imported {} cities from JSON", cities.size());
            citiesRepository.saveAll(cities);
            logger.info("Cities saved to database");
        } catch (Exception e) {
            logger.error("Failed to deserialize JSON: {}", e.getMessage());
            throw e;
        }
    }
}