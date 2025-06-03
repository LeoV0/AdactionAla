package com.ada.adaction_ala.service;

import com.ada.adaction_ala.model.VolunteerUpdateRequest;
import com.ada.adaction_ala.model.Volunteers;
import com.ada.adaction_ala.repository.VolunteersRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class VolunteersService {

    private static final Logger logger = LoggerFactory.getLogger(VolunteersService.class);

    private final VolunteersRepository volunteersRepository;
    private final ObjectMapper objectMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public VolunteersService(VolunteersRepository volunteersRepository, ObjectMapper objectMapper, BCryptPasswordEncoder passwordEncoder) {
        this.volunteersRepository = volunteersRepository;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Volunteers> findByUsername(String username) {
        return volunteersRepository.findByVl_username(username);
    }

    public Iterable<Volunteers> findAllVolunteers() {
        return volunteersRepository.findAll();
    }

    public Optional<Volunteers> findVolunteersById(Long id) {
        return volunteersRepository.findById(id);
    }

    public void deleteVolunteers(Long id) {
        volunteersRepository.deleteById(id);
    }

    public Volunteers saveVolunteers(Volunteers volunteers) {
        if (volunteers.getVl_password() != null) {
            volunteers.setVl_password(passwordEncoder.encode(volunteers.getVl_password()));
        }
        return volunteersRepository.save(volunteers);
    }

    public Optional<Volunteers> updateVolunteer(Long id, VolunteerUpdateRequest updateRequest) {
        Optional<Volunteers> optionalVolunteer = volunteersRepository.findById(id);
        if (optionalVolunteer.isEmpty()) {
            return Optional.empty();
        }

        Volunteers volunteer = optionalVolunteer.get();
        if (updateRequest.getFirstname() != null) {
            volunteer.setVl_firstname(updateRequest.getFirstname());
        }
        if (updateRequest.getPassword() != null) {
            volunteer.setVl_password(passwordEncoder.encode(updateRequest.getPassword()));
        }
        if (updateRequest.getLocation() != null) {
            volunteer.setVl_location(updateRequest.getLocation());
        }

        return Optional.of(volunteersRepository.save(volunteer));
    }

    public void importVolunteersFromJson() throws IOException {
        logger.info("Starting import of volunteers from JSON");
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
            for (Volunteers volunteer : volunteers) {
                if (volunteer.getVl_password() != null) {
                    volunteer.setVl_password(passwordEncoder.encode(volunteer.getVl_password()));
                }
            }
            volunteersRepository.saveAll(volunteers);
            logger.info("Volunteers saved to database");
        } catch (Exception e) {
            logger.error("Erreur de désérialisation : {}", e.getMessage());
            throw e;
        }
    }

    public Optional<Volunteers> login(String firstname, String password) {
        Optional<Volunteers> volunteer = volunteersRepository.findByVl_firstname(firstname);
        if (volunteer.isPresent() && passwordEncoder.matches(password, volunteer.get().getVl_password())) {
            return volunteer;
        }
        return Optional.empty();
    }
}