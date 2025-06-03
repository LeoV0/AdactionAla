package com.ada.adaction_ala.service;

import com.ada.adaction_ala.model.WasteTypes;
import com.ada.adaction_ala.repository.WasteTypesRepository;
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
public class WasteTypesService {

    private static final Logger logger = LoggerFactory.getLogger(WasteTypesService.class);

    private final WasteTypesRepository wasteTypesRepository;
    private final ObjectMapper objectMapper;

    public WasteTypesService(WasteTypesRepository wasteTypesRepository, ObjectMapper objectMapper) {
        this.wasteTypesRepository = wasteTypesRepository;
        this.objectMapper = objectMapper;
    }

    public Optional<WasteTypes> findWasteTypesById(Long id) {
        return wasteTypesRepository.findById(id);
    }

    public Iterable<WasteTypes> findAllWasteTypes() {
        return wasteTypesRepository.findAll();
    }

    public void deleteWasteTypes(Long id) {
        wasteTypesRepository.deleteById(id);
    }

    public WasteTypes saveWasteTypes(WasteTypes wasteTypes) {
        return wasteTypesRepository.save(wasteTypes);
    }

    public void importWasteTypesFromJson() throws IOException {
        logger.info("Starting import of waste types from JSON");
        ClassPathResource resource = new ClassPathResource("data/waste_types.json");
        if (!resource.exists()) {
            logger.error("File data/waste_types.json not found in classpath");
            throw new IOException("File data/waste_types.json not found");
        }
        String jsonContent = new String(resource.getInputStream().readAllBytes());
        logger.info("JSON content: {}", jsonContent);
        try {
            List<WasteTypes> wasteTypes = objectMapper.readValue(
                jsonContent,
                new TypeReference<List<WasteTypes>>() {}
            );
            logger.info("Imported {} waste types from JSON", wasteTypes.size());
            wasteTypesRepository.saveAll(wasteTypes);
            logger.info("Waste types saved to database");
        } catch (Exception e) {
            logger.error("Failed to deserialize JSON: {}", e.getMessage());
            throw e;
        }
    }
}