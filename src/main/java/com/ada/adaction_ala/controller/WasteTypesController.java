package com.ada.adaction_ala.controller;

import com.ada.adaction_ala.model.WasteTypes;
import com.ada.adaction_ala.service.WasteTypesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WasteTypesController {

    private final WasteTypesService wasteTypesService;

    public WasteTypesController(WasteTypesService wasteTypesService) {
        this.wasteTypesService = wasteTypesService;
    }

    @GetMapping("/waste_types")
    public Iterable<WasteTypes> getWasteTypes() {
        return wasteTypesService.findAllWasteTypes();
    }

    @PostMapping("/waste_types/import")
    public ResponseEntity<String> importWasteTypes() {
        try {
            wasteTypesService.importWasteTypesFromJson();
            return ResponseEntity.ok("Waste types imported successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to import waste types: " + e.getMessage());
        }
    }
}