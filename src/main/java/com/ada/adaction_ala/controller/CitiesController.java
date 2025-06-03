package com.ada.adaction_ala.controller;

import com.ada.adaction_ala.model.Cities;
import com.ada.adaction_ala.service.CitiesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CitiesController {

    private final CitiesService citiesService;

    public CitiesController(CitiesService citiesService) {
        this.citiesService = citiesService;
    }

    @GetMapping("/cities")
    public Iterable<Cities> getCities() {
        return citiesService.findAllCities();
    }

    @PostMapping("/cities/import")
    public ResponseEntity<String> importCities() {
        try {
            citiesService.importCitiesFromJson();
            return ResponseEntity.ok("Cities imported successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to import cities: " + e.getMessage());
        }
    }
}