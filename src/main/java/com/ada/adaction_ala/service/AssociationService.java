package com.ada.adaction_ala.service;

import com.ada.adaction_ala.model.Association;
import com.ada.adaction_ala.repository.AssociationRepository;
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
public class AssociationService {

    private static final Logger logger = LoggerFactory.getLogger(AssociationService.class);

    private final AssociationRepository associationRepository;
    private final ObjectMapper objectMapper;

    public AssociationService(AssociationRepository associationRepository, ObjectMapper objectMapper) {
        this.associationRepository = associationRepository;
        this.objectMapper = objectMapper;
    }

    public Optional<Association> findAssociationById(Long id) {
        return associationRepository.findById(id);
    }

    public Iterable<Association> findAllAssociations() {
        return associationRepository.findAll();
    }

    public void deleteAssociation(Long id) {
        associationRepository.deleteById(id);
    }

    public Association saveAssociation(Association association) {
        return associationRepository.save(association);
    }

    public void importAssociationsFromJson() throws IOException {
        logger.info("Starting import of associations from JSON");
        ClassPathResource resource = new ClassPathResource("data/associations.json");
        if (!resource.exists()) {
            logger.error("File data/associations.json not found in classpath");
            throw new IOException("File data/associations.json not found");
        }
        String jsonContent = new String(resource.getInputStream().readAllBytes());
        logger.info("JSON content: {}", jsonContent);
        try {
            List<Association> associations = objectMapper.readValue(
                jsonContent,
                new TypeReference<List<Association>>() {}
            );
            logger.info("Imported {} associations from JSON", associations.size());
            associationRepository.saveAll(associations);
            logger.info("Associations saved to database");
        } catch (Exception e) {
            logger.error("Failed to deserialize JSON: {}", e.getMessage());
            throw e;
        }
    }
}