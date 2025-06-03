package com.ada.adaction_ala.controller;

import com.ada.adaction_ala.model.VolunteerUpdateRequest;
import com.ada.adaction_ala.model.Volunteers;
import com.ada.adaction_ala.service.VolunteersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class VolunteersController {

    private final VolunteersService volunteersService;

    public VolunteersController(VolunteersService volunteersService) {
        this.volunteersService = volunteersService;
    }

    @GetMapping("/volunteers")
    public Iterable<Volunteers> getVolunteers() {
        return volunteersService.findAllVolunteers();
    }

    @PutMapping("/volunteers/{id}")
    public ResponseEntity<Volunteers> updateVolunteer(@PathVariable Long id, @RequestBody VolunteerUpdateRequest updateRequest) {
        Optional<Volunteers> updatedVolunteer = volunteersService.updateVolunteer(id, updateRequest);
        return updatedVolunteer.map(ResponseEntity::ok)
                              .orElseGet(() -> ResponseEntity.notFound().build());
    }

     @PostMapping("/volunteers/login")
    public ResponseEntity<Volunteers> login(@RequestParam String firstname, @RequestParam String password) {
        Optional<Volunteers> volunteer = volunteersService.login(firstname, password);
        return volunteer.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.status(401).body(null));
    }

    @PostMapping("/volunteers/import")
    public ResponseEntity<String> importVolunteers() {
        try {
            volunteersService.importVolunteersFromJson();
            return ResponseEntity.ok("Volunteers imported successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to import volunteers: " + e.getMessage());
        }
    }
}