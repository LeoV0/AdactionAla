package com.ada.adaction_ala.controller;

import com.ada.adaction_ala.model.Association;
import com.ada.adaction_ala.service.AssociationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssociationController {

    private final AssociationService associationService;

    public AssociationController(AssociationService associationService) {
        this.associationService = associationService;
    }

    @GetMapping("/associations")
    public Iterable<Association> getAssociations() {
        return associationService.findAllAssociations();
    }

    @PostMapping("/associations/import")
    public ResponseEntity<String> importAssociations() {
        try {
            associationService.importAssociationsFromJson();
            return ResponseEntity.ok("Associations imported successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to import associations: " + e.getMessage());
        }
    }
}