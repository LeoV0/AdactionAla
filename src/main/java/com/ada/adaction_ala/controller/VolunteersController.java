package com.ada.adaction_ala.controller;

import com.ada.adaction_ala.model.Volunteers;
import com.ada.adaction_ala.service.VolunteersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VolunteersController {

    private final VolunteersService volunteersService;

    public VolunteersController(VolunteersService volunteersService) {
        this.volunteersService = volunteersService;
    }

    @GetMapping("/volunteers")
    public Iterable<Volunteers> getAssociations() {
        return volunteersService.findAllVolunteers();
    }

    @PostMapping("/volunteers/import")
    public ResponseEntity<String> importVolunteers() {
        try {
            volunteersService.importVolunteersFromJson();
            return ResponseEntity.ok("Associations imported successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to import associations: " + e.getMessage());
        }
    }
}